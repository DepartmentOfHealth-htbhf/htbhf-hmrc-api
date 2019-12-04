package uk.gov.dhsc.htbhf.hmrc.factory;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.TestConstants.LISA_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.TestConstants.MAGGIE_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;

public class EligibilityResponseFactoryTest {

    private EligibilityResponseFactory eligibilityResponseFactory = new EligibilityResponseFactory();

    @Test
    void shouldCreateResponseFromHousehold() {
        Household household = aHousehold();

        EligibilityResponse response = eligibilityResponseFactory.createEligibilityResponse(household, EligibilityStatus.ELIGIBLE);

        assertThat(response.getEligibilityStatus()).isEqualTo(EligibilityStatus.ELIGIBLE);
        assertThat(response.getHouseholdIdentifier()).isEqualTo(household.getHouseholdIdentifier());
        assertThat(response.getNumberOfChildrenUnderFour()).isEqualTo(2);
        assertThat(response.getNumberOfChildrenUnderOne()).isEqualTo(1);
        assertThat(response.getChildren()).containsOnly(expectedChildren());
    }

    private ChildDTO[] expectedChildren() {
        ChildDTO childUnderOne = ChildDTO.builder().dateOfBirth(MAGGIE_DATE_OF_BIRTH).build();
        ChildDTO childUnderFour = ChildDTO.builder().dateOfBirth(LISA_DATE_OF_BIRTH).build();
        return new ChildDTO[]{childUnderOne, childUnderFour};
    }
}
