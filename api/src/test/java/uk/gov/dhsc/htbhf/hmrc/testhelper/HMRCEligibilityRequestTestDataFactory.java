package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCPersonDTOTestDataFactory.aValidHMRCPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.CTC_ANNUAL_INCOME_THRESHOLD;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.ELIGIBLE_END_DATE;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.ELIGIBLE_START_DATE;

public class HMRCEligibilityRequestTestDataFactory {

    public static HMRCEligibilityRequest aValidHMRCEligibilityRequest() {
        return aValidEligibilityRequestBuilder().build();
    }

    private static HMRCEligibilityRequest.HMRCEligibilityRequestBuilder aValidEligibilityRequestBuilder() {
        return HMRCEligibilityRequest.builder()
                .person(aValidHMRCPerson())
                .ctcAnnualIncomeThreshold(CTC_ANNUAL_INCOME_THRESHOLD)
                .eligibleStartDate(ELIGIBLE_START_DATE)
                .eligibleEndDate(ELIGIBLE_END_DATE);
    }
}
