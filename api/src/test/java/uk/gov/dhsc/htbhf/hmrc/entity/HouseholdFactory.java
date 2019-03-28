package uk.gov.dhsc.htbhf.hmrc.entity;

import java.time.LocalDate;

public class HouseholdFactory {

    public static Household aHousehold() {
        return aHouseholdWithNoAdultsOrChildren()
                .build()
                .addAdult(anAdult("Homer", "Simpson", "QQ123456C"))
                .addAdult(anAdult("Marge", "Simpson", "QQ123456D"))
                .addChild(aChild("Bart", "Simpson", 48))
                .addChild(aChild("Lisa", "Simpson", 24))
                .addChild(aChild("Maggie", "Simpson", 6));
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
                .addressLine1("742 Evergreen Terrace")
                .addressLine2("Springfield")
                .postcode("AA11AA")
                .awardDate(LocalDate.now())
                .build();
    }

}
