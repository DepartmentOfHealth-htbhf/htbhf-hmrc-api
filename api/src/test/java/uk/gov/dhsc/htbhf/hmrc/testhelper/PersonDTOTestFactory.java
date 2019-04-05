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
        return aValidPersonBuilder().build();
    }

    public static PersonDTO aPersonWithFirstName(String forename) {
        return aValidPersonBuilder().firstName(forename).build();
    }

    public static PersonDTO aPersonWithLastName(String surname) {
        return aValidPersonBuilder().lastName(surname).build();
    }

    public static PersonDTO aPersonWithNino(String nino) {
        return aValidPersonBuilder().nino(nino).build();
    }

    public static PersonDTO aPersonWithDateOfBirth(LocalDate dateOfBirth) {
        return aValidPersonBuilder().dateOfBirth(dateOfBirth).build();
    }

    public static PersonDTO aPersonWithAddress(AddressDTO address) {
        return aValidPersonBuilder().address(address).build();
    }

    public static PersonDTO.PersonDTOBuilder aValidPersonBuilder() {
        return PersonDTO.builder()
                .dateOfBirth(LISA_DOB)
                .nino(LISA_NINO)
                .address(aValidAddress())
                .firstName(LISA_FORENAME)
                .lastName(LISA_SURNAME);
    }

}
