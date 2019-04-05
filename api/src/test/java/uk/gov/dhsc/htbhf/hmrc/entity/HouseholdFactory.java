package uk.gov.dhsc.htbhf.hmrc.entity;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.*;

public class HouseholdFactory {

    public static Household aHousehold() {
        return aHouseholdWithNoAdultsOrChildren()
                .build()
                .addAdult(anAdult(HOMER_FORENAME, SIMPSONS_SURNAME, HOMER_NINO))
                .addAdult(anAdult(MARGE_FORENAME, SIMPSONS_SURNAME, MARGE_NINO))
                .addChild(aChild("Bart", SIMPSONS_SURNAME, 48))
                .addChild(aChild(LISA_FORENAME, SIMPSONS_SURNAME, 24))
                .addChild(aChild("Maggie", SIMPSONS_SURNAME, 6));
    }

    public static Household.HouseholdBuilder aHouseholdWithNoAdultsOrChildren() {
        return Household.builder()
                .fileImportNumber(1)
                .householdIdentifier("aHouseholdIdentifier")
                .build()
                .toBuilder();
    }

    public static Child aChild(String forename, String surname, int ageInMonths) {
        return Child.builder()
                .firstForename(forename)
                .surname(surname)
                .dateOfBirth(LocalDate.now().minusMonths(ageInMonths))
                .build();
    }

    public static Adult anAdult(String forename, String surname, String nino) {
        return Adult.builder()
                .firstForename(forename)
                .surname(surname)
                .nino(nino)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .addressLine2(SIMPSONS_ADDRESS_LINE_2)
                .postcode(SIMPSONS_POSTCODE)
                .awardDate(LocalDate.now())
                .build();
    }

    public static Adult anAdultWithNino(String nino) {
        return Adult.builder()
                .firstForename(HOMER_FORENAME)
                .surname(SIMPSONS_SURNAME)
                .nino(nino)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .addressLine2(SIMPSONS_ADDRESS_LINE_2)
                .postcode(SIMPSONS_POSTCODE)
                .awardDate(LocalDate.now())
                .build();
    }

}
