package uk.gov.dhsc.htbhf.hmrc.service;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.hmrc.entity.Adult;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.entity.HouseholdFactory.aHouseholdWithNoAdultsOrChildren;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCPersonDTOTestDataFactory.aValidHMRCPerson;

/**
 * To match a valid address use address data defined in AddressDTOTestDataFactory.
 */
public class HouseholdVerifierTest {

    private final HouseholdVerifier householdVerifier = new HouseholdVerifier();

    @Test
    void shouldReturnTrueWhenPersonMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("Flat b")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnFalseWhenSurnameDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Smith")
                .addressLine1("Flat b")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnFalseWhenAddressLine1DoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("Fake apartment")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnFalseWhenPostcodeDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("742 Evergreen Terrace")
                .postcode("W1 1NA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAddressLine1FirstSixCharactersMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("Flat b_DIFFERENT")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnTrueWhenAddressLine1FirstSixCharactersDifferentCaseMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("FLAT B_DIFFERENT")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnFalseWhenAddressLine1UnderSixCharacterDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("Flat")
                .postcode("AA11AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnTrueWhenPostcodeMatchesWithSpacesHousehold() {
        Adult adult = Adult.builder()
                .firstForename("Lisa")
                .surname("Simpson")
                .addressLine1("Flat b")
                .postcode("AA1 1AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

}
