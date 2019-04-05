package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.AddressDTO;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_DOB;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_FORENAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_NINO;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_SURNAME;

public class PersonDTOTestFactory {

    public static PersonDTO aValidPerson() {
        return buildDefaultPerson().build();
    }

    public static PersonDTO aPersonWithFirstName(String forename) {
        return buildDefaultPerson().firstName(forename).build();
    }

    public static PersonDTO aPersonWithLastName(String surname) {
        return buildDefaultPerson().lastName(surname).build();
    }

    public static PersonDTO aPersonWithNino(String nino) {
        return buildDefaultPerson().nino(nino).build();
    }

    public static PersonDTO aPersonWithDateOfBirth(LocalDate dateOfBirth) {
        return buildDefaultPerson().dateOfBirth(dateOfBirth).build();
    }

    public static PersonDTO aPersonWithAddress(AddressDTO address) {
        return buildDefaultPerson().address(address).build();
    }

    private static PersonDTO.PersonDTOBuilder buildDefaultPerson() {
        return PersonDTO.builder()
                .dateOfBirth(LISA_DOB)
                .nino(LISA_NINO)
                .address(aValidAddress())
                .firstName(LISA_FORENAME)
                .lastName(LISA_SURNAME);
    }

}
