package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestFactory.aValidPerson;

public class EligibilityRequestTestFactory {

    private static final LocalDate ELIGIBLE_END_DATE = LocalDate.parse("2019-03-01");
    private static final LocalDate ELIGIBLE_START_DATE = LocalDate.parse("2019-02-14");
    private static final BigDecimal CTC_MONTHLY_INCOME_THRESHOLD = BigDecimal.valueOf(11000);

    public static EligibilityRequest anEligibilityRequest() {
        return buildDefaultRequest().build();
    }

    public static EligibilityRequest anEligibilityRequestWithPerson(PersonDTO personDTO) {
        return buildDefaultRequest().person(personDTO).build();
    }

    public static EligibilityRequest anEligibilityRequestWithCtcAnnualIncomeThreshold(BigDecimal threshold) {
        return buildDefaultRequest().ctcAnnualIncomeThreshold(threshold).build();
    }

    public static EligibilityRequest anEligibilityRequestWithEligibileStartDate(LocalDate startDate) {
        return buildDefaultRequest().eligibleStartDate(startDate).build();
    }

    public static EligibilityRequest anEligibilityRequestWithEligibileEndDate(LocalDate endDate) {
        return buildDefaultRequest().eligibleEndDate(endDate).build();
    }

    private static EligibilityRequest.EligibilityRequestBuilder buildDefaultRequest() {
        return EligibilityRequest.builder()
                .person(aValidPerson())
                .eligibleStartDate(ELIGIBLE_START_DATE)
                .eligibleEndDate(ELIGIBLE_END_DATE)
                .ctcAnnualIncomeThreshold(CTC_MONTHLY_INCOME_THRESHOLD);
    }
}
