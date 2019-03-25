package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityStatus;

public class EligibilityResponseTestFactory {

    public static EligibilityResponse anEligibilityResponse() {
        return EligibilityResponse.builder()
                .eligibilityStatus(EligibilityStatus.ELIGIBLE)
                .householdIdentifier("household1")
                .numberOfChildrenUnderFour(1)
                .numberOfChildrenUnderOne(1)
                .build();
    }
}
