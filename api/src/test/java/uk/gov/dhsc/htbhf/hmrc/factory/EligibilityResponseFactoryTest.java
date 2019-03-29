package uk.gov.dhsc.htbhf.hmrc.factory;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.entity.HouseholdFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.factory.EligibilityResponseFactory.createEligibilityResponse;


public class EligibilityResponseFactoryTest {

    @Test
    void shouldCreateResponseFromHousehold() {
        Household household = aHousehold();

        EligibilityResponse response = createEligibilityResponse(household);

        assertThat(response.getHouseholdIdentifier()).isEqualTo(household.getHouseholdIdentifier());
        assertThat(response.getNumberOfChildrenUnderFour()).isEqualTo(2);
        assertThat(response.getNumberOfChildrenUnderOne()).isEqualTo(1);
    }
}
