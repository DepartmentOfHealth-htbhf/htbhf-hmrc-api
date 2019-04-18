package uk.gov.dhsc.htbhf.hmrc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus;
import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.ELIGIBLE;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.NO_MATCH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.aValidEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.anEligibilityRequestWithPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.aValidEligibilityResponseBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.anEligibilityResponse;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCEligibilityRequestTestDataFactory.aValidHMRCEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHouseholdWithChildrenAged6and24months;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.aValidPersonBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.HOMER_NINO;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.HOUSEHOLD_INDENTIFIER;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HMRCIntegrationTests {

    private static final URI ENDPOINT = URI.create("/v1/hmrc/eligibility");

    private static final String HMRC_URL = "http://localhost:8130/v1/hmrc/benefits";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HouseholdRepository householdRepository;

    @MockBean
    private RestTemplate restTemplateWithIdHeaders;

    @Test
    void shouldReturnEligibilityResponseWhenNotInDatabase() {
        //Given
        ResponseEntity<EligibilityResponse> hmrcEligibilityResponse = new ResponseEntity<>(anEligibilityResponse(), OK);
        given(restTemplateWithIdHeaders.postForEntity(anyString(), any(), eq(EligibilityResponse.class))).willReturn(hmrcEligibilityResponse);

        //When
        ResponseEntity<EligibilityResponse> response = callService(aValidEligibilityRequest());

        //Then
        assertResponseCorrectWithHouseholdDetails(response, HOUSEHOLD_INDENTIFIER, ELIGIBLE);
        verify(restTemplateWithIdHeaders).postForEntity(HMRC_URL, aValidHMRCEligibilityRequest(), EligibilityResponse.class);
    }

    @ParameterizedTest(name = "Should return eligible response for claimant [{0}] Simpson stored in UC household table")
    @CsvSource({"Homer, EB654321B",
            "Marge, EB876543A"})
    void shouldReturnEligibleWhenMatchesHouseholdInDatabase(String parentName, String nino) {
        //Given
        householdRepository.save(aHouseholdWithChildrenAged6and24months());
        PersonDTO person = aValidPersonBuilder().firstName(parentName).nino(nino).build();
        EligibilityRequest eligibilityRequest = anEligibilityRequestWithPerson(person);

        //When
        ResponseEntity<EligibilityResponse> response = callService(eligibilityRequest);

        //Then
        assertResponseCorrectWithHouseholdDetails(response, HOUSEHOLD_INDENTIFIER, ELIGIBLE);
        verifyZeroInteractions(restTemplateWithIdHeaders);
        householdRepository.deleteAll();
    }

    @Test
    void shouldReturnNoMatchWhenMatchesNinoButNotNameInDatabase() {
        //Given
        householdRepository.save(aHousehold());
        PersonDTO person = aValidPersonBuilder().lastName("noMatch").nino(HOMER_NINO).build();
        EligibilityRequest eligibilityRequest = anEligibilityRequestWithPerson(person);

        //When
        ResponseEntity<EligibilityResponse> response = callService(eligibilityRequest);

        //Then
        assertResponseCorrectWithStatusOnly(response, NO_MATCH);
        verifyZeroInteractions(restTemplateWithIdHeaders);
        householdRepository.deleteAll();
    }

    private ResponseEntity<EligibilityResponse> callService(EligibilityRequest eligibilityRequest) {
        return restTemplate.exchange(buildRequestEntity(eligibilityRequest), EligibilityResponse.class);
    }

    private void assertResponseCorrectWithHouseholdDetails(ResponseEntity<EligibilityResponse> response, String householdIdentifier, EligibilityStatus status) {
        EligibilityResponse expectedResponse = aValidEligibilityResponseBuilder()
                .householdIdentifier(householdIdentifier)
                .eligibilityStatus(status)
                .build();
        assertResponseCorrect(response, expectedResponse);
    }

    private void assertResponseCorrectWithStatusOnly(ResponseEntity<EligibilityResponse> response, EligibilityStatus status) {
        EligibilityResponse expectedResponse = EligibilityResponse.builder()
                .eligibilityStatus(status)
                .build();
        assertResponseCorrect(response, expectedResponse);
    }

    private void assertResponseCorrect(ResponseEntity<EligibilityResponse> response, EligibilityResponse expectedResponse) {
        assertThat(response.getStatusCode()).isEqualTo(OK);
        EligibilityResponse eligibilityResponse = response.getBody();
        assertThat(eligibilityResponse).isNotNull();
        assertEligibilityResponse(expectedResponse, eligibilityResponse);
    }

    // checks that two eligibility responses are equal whilst ignoring the order of the children
    private void assertEligibilityResponse(EligibilityResponse expectedResponse, EligibilityResponse eligibilityResponse) {
        assertThat(eligibilityResponse).isEqualToIgnoringGivenFields(expectedResponse, "children");
        assertChildren(expectedResponse.getChildren(), eligibilityResponse.getChildren());
    }

    private void assertChildren(List<ChildDTO> expected, List<ChildDTO> actual) {
        if (expected == null) {
            assertThat(actual).isNull();
        } else {
            assertThat(expected).containsOnlyElementsOf(actual);
        }
    }

    private RequestEntity buildRequestEntity(Object requestObject) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new RequestEntity<>(requestObject, headers, HttpMethod.POST, ENDPOINT);
    }
}
