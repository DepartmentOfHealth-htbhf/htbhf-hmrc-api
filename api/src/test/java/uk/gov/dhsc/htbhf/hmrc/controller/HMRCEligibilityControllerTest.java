package uk.gov.dhsc.htbhf.hmrc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dhsc.htbhf.hmrc.converter.EligibilityRequestToHMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.service.EligibilityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestDataFactory.aValidEligibilityRequest;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityResponseTestDataFactory.anEligibilityResponse;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCEligibilityRequestTestDataFactory.aValidHMRCEligibilityRequest;

@ExtendWith(MockitoExtension.class)
class HMRCEligibilityControllerTest {

    @InjectMocks
    private HMRCEligibilityController controller;

    @Mock
    private EligibilityService eligibilityService;

    @Mock
    private EligibilityRequestToHMRCEligibilityRequest converter;

    @Test
    void shouldReturnEligibilityResponse() {
        EligibilityRequest eligibilityRequest = aValidEligibilityRequest();
        HMRCEligibilityRequest hmrcEligibilityRequest = aValidHMRCEligibilityRequest();
        given(converter.convert(any())).willReturn(hmrcEligibilityRequest);
        given(eligibilityService.checkEligibility(any())).willReturn(anEligibilityResponse());

        EligibilityResponse response = controller.getBenefits(eligibilityRequest);

        assertThat(response).isEqualTo(anEligibilityResponse());
        verify(converter).convert(eligibilityRequest);
        verify(eligibilityService).checkEligibility(hmrcEligibilityRequest);
    }

}
