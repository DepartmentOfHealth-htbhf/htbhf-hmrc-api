package uk.gov.dhsc.htbhf.hmrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;
import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.net.URI;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.ELIGIBLE;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.NO_MATCH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.aValidEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.anEligibilityRequestWithPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.aValidEligibilityResponseBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.anEligibilityResponse;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHouseholdWithChildrenAged6and24months;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.aPersonWithNino;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.aValidPersonBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.HOMER_NINO;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.HOUSEHOLD_INDENTIFIER;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8130)
public class HMRCIntegrationTests {

    private static final URI ENDPOINT = URI.create("/v1/hmrc/eligibility");

    private static final String HMRC_URL = "/v1/hmrc/benefits";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        WireMock.reset();
    }

    @Test
    void shouldReturnEligibilityResponseWhenNotInDatabase() throws JsonProcessingException {
        //Given
        EligibilityResponse hmrcEligibilityResponse = anEligibilityResponse();
        stubHMRCEndpointWithSuccessfulResponse(hmrcEligibilityResponse);

        //When
        ResponseEntity<EligibilityResponse> response = callService(aValidEligibilityRequest());

        //Then
        assertResponseCorrectWithHouseholdDetails(response, HOUSEHOLD_INDENTIFIER, ELIGIBLE);
        verifyHMRCEndpointCalled();
    }

    @Test
    void shouldReturnBadRequestForInvalidEligibilityRequest() {
        PersonDTO invalidPerson = aPersonWithNino(null);
        EligibilityRequest request = anEligibilityRequestWithPerson(invalidPerson);

        ResponseEntity<ErrorResponse> errorResponse = restTemplate.postForEntity(ENDPOINT, request, ErrorResponse.class);

        assertValidationErrorInResponse(errorResponse, "person.nino", "must not be null");
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
        verifyHMRCEndpointNotCalled();
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
        verifyHMRCEndpointNotCalled();
        householdRepository.deleteAll();
    }

    private void stubHMRCEndpointWithSuccessfulResponse(EligibilityResponse eligibilityResponse) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(eligibilityResponse);
        stubFor(post(urlEqualTo(HMRC_URL)).willReturn(okJson(json)));
    }

    private void verifyHMRCEndpointCalled() {
        verify(exactly(1), postRequestedFor(urlEqualTo(HMRC_URL)));
    }

    private void verifyHMRCEndpointNotCalled() {
        verify(exactly(0), postRequestedFor(urlEqualTo(HMRC_URL)));
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
