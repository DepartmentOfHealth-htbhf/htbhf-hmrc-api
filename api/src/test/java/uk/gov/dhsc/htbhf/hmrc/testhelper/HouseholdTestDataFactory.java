package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.entity.Adult;
import uk.gov.dhsc.htbhf.hmrc.entity.Child;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.TestConstants.*;

public class HouseholdTestDataFactory {

    public static Household aHousehold() {
        return aHouseholdWithChildrenAged6and24months();
    }

    public static Household aHouseholdWithChildrenAged6and24months() {
        return aHouseholdWithNoAdultsOrChildren()
                .build()
                .addAdult(anAdult(HOMER_FORENAME, SIMPSON_SURNAME, HOMER_NINO))
                .addAdult(anAdult(MARGE_FORENAME, SIMPSON_SURNAME, MARGE_NINO))
                .addChild(aChild(BART_FORENAME, SIMPSON_SURNAME, BART_DATE_OF_BIRTH))
                .addChild(aChild(LISA_FORENAME, SIMPSON_SURNAME, LISA_DATE_OF_BIRTH))
                .addChild(aChild(MAGGIE_FORENAME, SIMPSON_SURNAME, MAGGIE_DATE_OF_BIRTH));
    }

    public static Household.HouseholdBuilder aHouseholdWithNoAdultsOrChildren() {
        return Household.builder()
                .fileImportNumber(1)
                .householdIdentifier(HMRC_HOUSEHOLD_IDENTIFIER)
                .build()
                .toBuilder();
    }

    private static Child aChild(String forename, String surname, LocalDate dateOfBirth) {
        return Child.builder()
                .firstForename(forename)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .build();
    }

    private static Adult anAdult(String forename, String surname, String nino) {
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
                .surname(SIMPSON_SURNAME)
                .nino(nino)
                .addressLine1(SIMPSONS_ADDRESS_LINE_1)
                .addressLine2(SIMPSONS_ADDRESS_LINE_2)
                .postcode(SIMPSONS_POSTCODE)
                .awardDate(LocalDate.now())
                .build();
    }

}
