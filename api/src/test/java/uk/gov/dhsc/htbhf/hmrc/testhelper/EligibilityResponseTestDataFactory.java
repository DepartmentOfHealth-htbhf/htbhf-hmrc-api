package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;

import java.util.List;

import static java.util.Arrays.asList;
import static uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus.ELIGIBLE;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.HOUSEHOLD_INDENTIFIER;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.MAGGIE_DATE_OF_BIRTH;

public class EligibilityResponseTestDataFactory {

    public static EligibilityResponse anEligibilityResponse() {
        return aValidEligibilityResponseBuilder().build();
    }

    /**
     * Returns an eligibility response matching the household from HouseholdTestDataFactory.
     * @return an eligibility response matching the household from HouseholdTestDataFactory.
     */
    public static EligibilityResponse.EligibilityResponseBuilder aValidEligibilityResponseBuilder() {
        return EligibilityResponse.builder()
                .eligibilityStatus(ELIGIBLE)
                .numberOfChildrenUnderOne(1)
                .numberOfChildrenUnderFour(2)
                .householdIdentifier(HOUSEHOLD_INDENTIFIER)
                .children(createChildren());
    }

    private static List<ChildDTO> createChildren() {
        ChildDTO childUnderOne = ChildDTO.builder().dateOfBirth(MAGGIE_DATE_OF_BIRTH).build();
        ChildDTO childUnderFour = ChildDTO.builder().dateOfBirth(LISA_DATE_OF_BIRTH).build();
        return asList(childUnderOne, childUnderFour);
    }
}
