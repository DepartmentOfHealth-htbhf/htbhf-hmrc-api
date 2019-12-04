package uk.gov.dhsc.htbhf.hmrc;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.*;
import uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;
import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.TestConstants.HMRC_HOUSEHOLD_IDENTIFIER;
import static uk.gov.dhsc.htbhf.TestConstants.HOMER_NINO_V1;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.ELIGIBLE;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.NO_MATCH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.aValidEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.anEligibilityRequestWithPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.aValidEligibilityResponseBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHouseholdWithChildrenAged6and24months;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.aPersonWithNino;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.aValidPersonBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8130)
public class HMRCIntegrationTests {

    private static final URI ENDPOINT = URI.create("/v1/hmrc/eligibility");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HouseholdRepository householdRepository;

    @AfterEach
    public void tearDown() {
        WireMock.reset();
    }

    @Test
    void shouldReturnNoMatchResponseWhenNotInDatabase() {
        //When
        ResponseEntity<EligibilityResponse> response = callService(aValidEligibilityRequest());

        //Then
        assertResponseCorrectWithStatusOnly(response, NO_MATCH);
    }

    @Test
    void shouldReturnBadRequestForInvalidEligibilityRequest() {
        PersonDTO invalidPerson = aPersonWithNino(null);
        EligibilityRequest request = anEligibilityRequestWithPerson(invalidPerson);

        ResponseEntity<ErrorResponse> errorResponse = restTemplate.postForEntity(ENDPOINT, request, ErrorResponse.class);

        assertValidationErrorInResponse(errorResponse, "person.nino", "must not be null");
    }

    @ParameterizedTest(name = "Should return eligible response for claimant [{0}] Simpson stored in UC household table")
    @CsvSource({"Homer, EE123456C",
            "Marge, EB123456D"})
    void shouldReturnEligibleWhenMatchesHouseholdInDatabase(String parentName, String nino) {
        //Given
        householdRepository.save(aHouseholdWithChildrenAged6and24months());
        PersonDTO person = aValidPersonBuilder().firstName(parentName).nino(nino).build();
        EligibilityRequest eligibilityRequest = anEligibilityRequestWithPerson(person);

        //When
        ResponseEntity<EligibilityResponse> response = callService(eligibilityRequest);

        //Then
        assertResponseCorrectWithHouseholdDetails(response, HMRC_HOUSEHOLD_IDENTIFIER, ELIGIBLE);
        householdRepository.deleteAll();
    }

    @Test
    void shouldReturnNoMatchWhenMatchesNinoButNotNameInDatabase() {
        //Given
        householdRepository.save(aHousehold());
        PersonDTO person = aValidPersonBuilder().lastName("noMatch").nino(HOMER_NINO_V1).build();
        EligibilityRequest eligibilityRequest = anEligibilityRequestWithPerson(person);

        //When
        ResponseEntity<EligibilityResponse> response = callService(eligibilityRequest);

        //Then
        assertResponseCorrectWithStatusOnly(response, NO_MATCH);
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
