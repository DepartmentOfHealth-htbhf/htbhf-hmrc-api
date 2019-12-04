package uk.gov.dhsc.htbhf.hmrc.service;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.hmrc.entity.Adult;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.dhsc.htbhf.TestConstants.LISA_FORENAME;
import static uk.gov.dhsc.htbhf.TestConstants.SIMPSONS_ADDRESS_LINE_1;
import static uk.gov.dhsc.htbhf.TestConstants.SIMPSONS_POSTCODE;
import static uk.gov.dhsc.htbhf.TestConstants.SIMPSON_SURNAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HMRCPersonDTOTestDataFactory.aValidHMRCPerson;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHouseholdWithNoAdultsOrChildren;

/**
 * To match a valid address use address data defined in AddressDTOTestDataFactory.
 */
public class HouseholdVerifierTest {

    private final HouseholdVerifier householdVerifier = new HouseholdVerifier();

    @Test
    void shouldReturnTrueWhenPersonMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnFalseWhenSurnameDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname("Smith")
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnFalseWhenAddressLine1DoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1("Fake apartment")
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnFalseWhenPostcodeDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .postcode("W1 1NA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAddressLine1FirstSixCharactersMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1("742 Evenwood Drive")
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnTrueWhenAddressLine1FirstSixCharactersDifferentCaseMatchesHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1.toUpperCase())
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

    @Test
    void shouldReturnFalseWhenAddressLine1UnderSixCharacterDoesNotMatchHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1("Flat")
                .postcode(SIMPSONS_POSTCODE)
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isFalse();
    }

    @Test
    void shouldReturnTrueWhenPostcodeMatchesWithSpacesHousehold() {
        Adult adult = Adult.builder()
                .firstForename(LISA_FORENAME)
                .surname(SIMPSON_SURNAME)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .postcode("AA1 1AA")
                .build();
        Household household = aHouseholdWithNoAdultsOrChildren().build().addAdult(adult);

        Boolean response = householdVerifier.detailsMatch(household, aValidHMRCPerson());

        assertThat(response).isTrue();
    }

}
