package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;

import static uk.gov.dhsc.htbhf.hmrc.model.EligibilityStatus.ELIGIBLE;

public class EligibilityResponseTestFactory {


    public static final String HOUSEHOLD_INDENTIFIER = "household1";

    public static EligibilityResponse anEligibilityResponse() {
        return aValidEligibilityResponseBuilder().build();
    }

    public static EligibilityResponse.EligibilityResponseBuilder aValidEligibilityResponseBuilder() {
        return EligibilityResponse.builder()
                .eligibilityStatus(ELIGIBLE)
                .numberOfChildrenUnderOne(1)
                .numberOfChildrenUnderFour(1)
                .householdIdentifier(HOUSEHOLD_INDENTIFIER);
    }
}
