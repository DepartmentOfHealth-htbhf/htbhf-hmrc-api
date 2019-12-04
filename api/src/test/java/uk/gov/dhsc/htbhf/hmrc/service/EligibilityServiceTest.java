package uk.gov.dhsc.htbhf.hmrc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.factory.EligibilityResponseFactory;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.ELIGIBLE;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.aValidEligibilityResponseBuilder;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.anEligibilityResponseWithNoMatchStatus;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCEligibilityRequestTestDataFactory.aValidHMRCEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;

@ExtendWith(MockitoExtension.class)
class EligibilityServiceTest {

    @Mock
    private HouseholdRepository repository;

    @Mock
    private HouseholdVerifier householdVerifier;

    @Mock
    private EligibilityResponseFactory eligibilityResponseFactory;

    @InjectMocks
    private EligibilityService eligibilityService;

    @BeforeEach
    public void setUp() {
        eligibilityService = new EligibilityService(repository, householdVerifier, eligibilityResponseFactory);
    }

    @Test
    void shouldReturnResponseFromDatabaseIfFound() {
        HMRCEligibilityRequest eligibilityRequest = aValidHMRCEligibilityRequest();
        Household household = aHousehold();
        given(repository.findHouseholdByAdultWithNino(anyString())).willReturn(Optional.of(household));
        given(householdVerifier.detailsMatch(any(Household.class), any())).willReturn(true);
        given(eligibilityResponseFactory.createEligibilityResponse(household, ELIGIBLE))
                .willReturn(aValidEligibilityResponseBuilder().build());

        EligibilityResponse response = eligibilityService.checkEligibility(eligibilityRequest);

        assertThat(response.getNumberOfChildrenUnderFour()).isEqualTo(2);
        assertThat(response.getNumberOfChildrenUnderOne()).isEqualTo(1);
        assertThat(response.getHouseholdIdentifier()).isEqualTo(household.getHouseholdIdentifier());

        verify(repository).findHouseholdByAdultWithNino(eligibilityRequest.getPerson().getNino());
        verify(householdVerifier).detailsMatch(household, eligibilityRequest.getPerson());
        verify(eligibilityResponseFactory).createEligibilityResponse(household, ELIGIBLE);
    }

    @Test
    void shouldReturnNoMatchIFNoHouseholdFoundInDatabase() {
        HMRCEligibilityRequest eligibilityRequest = aValidHMRCEligibilityRequest();
        given(repository.findHouseholdByAdultWithNino(anyString())).willReturn(Optional.empty());

        EligibilityResponse response = eligibilityService.checkEligibility(eligibilityRequest);

        assertThat(response).isEqualTo(anEligibilityResponseWithNoMatchStatus());
        verify(repository).findHouseholdByAdultWithNino(eligibilityRequest.getPerson().getNino());
        verifyNoInteractions(householdVerifier);
    }
}
