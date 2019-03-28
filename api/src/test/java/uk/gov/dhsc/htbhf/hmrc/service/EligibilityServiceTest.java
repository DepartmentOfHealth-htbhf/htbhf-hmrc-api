package uk.gov.dhsc.htbhf.hmrc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.hmrc.entity.HouseholdFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestFactory.anEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestFactory.anEligibilityResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EligibilityServiceTest {

    private static final String ENDPOINT = "/v1/hmrc/benefits";

    @Value("${hmrc.base-uri}")
    private String hmrcUri;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private HouseholdRepository repository;

    @MockBean
    private HouseholdVerifier householdVerifier;

    @Autowired
    private EligibilityService eligibilityService;

    @Test
    void shouldReturnResponseFromDatabaseIfFound() {
        EligibilityRequest eligibilityRequest = anEligibilityRequest();
        Household household = aHousehold();
        given(repository.findHouseholdByAdultWithNino(anyString())).willReturn(Optional.of(household));
        given(householdVerifier.detailsMatch(any(Household.class), any())).willReturn(true);

        EligibilityResponse response = eligibilityService.checkEligibility(eligibilityRequest);

        assertThat(response.getNumberOfChildrenUnderFour()).isEqualTo(2);
        assertThat(response.getNumberOfChildrenUnderOne()).isEqualTo(1);
        assertThat(response.getHouseholdIdentifier()).isEqualTo(household.getHouseholdIdentifier());

        verify(repository).findHouseholdByAdultWithNino(eligibilityRequest.getPerson().getNino());
        verify(householdVerifier).detailsMatch(household, eligibilityRequest.getPerson());
        verifyZeroInteractions(restTemplate);
    }

    @Test
    void shouldCallHMRCService() {
        EligibilityRequest eligibilityRequest = anEligibilityRequest();
        given(repository.findHouseholdByAdultWithNino(anyString())).willReturn(Optional.empty());
        given(restTemplate.postForEntity(anyString(), any(), any()))
                .willReturn(new ResponseEntity<>(anEligibilityResponse(), OK));

        EligibilityResponse response = eligibilityService.checkEligibility(eligibilityRequest);

        assertThat(response).isEqualTo(anEligibilityResponse());
        verify(repository).findHouseholdByAdultWithNino(eligibilityRequest.getPerson().getNino());
        verifyZeroInteractions(householdVerifier);
        verify(restTemplate).postForEntity(hmrcUri + ENDPOINT, eligibilityRequest, EligibilityResponse.class);
    }
}
