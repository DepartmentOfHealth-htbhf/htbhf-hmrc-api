package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonTestFactory.aPerson;

public class EligibilityRequestTestFactory {

    private static final LocalDate ELIGIBLE_END_DATE = LocalDate.parse("2019-03-01");
    private static final LocalDate ELIGIBLE_START_DATE = LocalDate.parse("2019-02-14");
    private static final BigDecimal CTC_MONTHLY_INCOME_THRESHOLD = BigDecimal.valueOf(11000);

    public static EligibilityRequest anEligibilityRequest() {
        return buildDefaultRequest().build();
    }

    public static EligibilityRequest.EligibilityRequestBuilder buildDefaultRequest() {
        return EligibilityRequest.builder()
                .person(aPerson())
                .eligibleStartDate(ELIGIBLE_START_DATE)
                .eligibleEndDate(ELIGIBLE_END_DATE)
                .ctcAnnualIncomeThreshold(CTC_MONTHLY_INCOME_THRESHOLD);
    }
}
