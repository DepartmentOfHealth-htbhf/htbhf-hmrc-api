package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCPersonDTOTestDataFactory.aValidHMRCPerson;

public class HMRCEligibilityRequestTestDataFactory {
    private static final BigDecimal CTC_ANNUAL_INCOME_THRESHOLD = new BigDecimal(408);
    private static final LocalDate ELIGIBLE_START_DATE = LocalDate.parse("2019-01-01");
    private static final LocalDate ELIGIBLE_END_DATE = LocalDate.parse("2019-02-01");

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
