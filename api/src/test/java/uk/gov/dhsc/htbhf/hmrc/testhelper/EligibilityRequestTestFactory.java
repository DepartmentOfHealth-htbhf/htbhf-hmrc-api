package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestFactory.aValidPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.CTC_ANNUAL_INCOME_THRESHOLD;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.ELIGIBLE_END_DATE;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.ELIGIBLE_START_DATE;

public class EligibilityRequestTestFactory {

    public static EligibilityRequest aValidEligibilityRequest() {
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
                .ctcAnnualIncomeThreshold(CTC_ANNUAL_INCOME_THRESHOLD);
    }
}
