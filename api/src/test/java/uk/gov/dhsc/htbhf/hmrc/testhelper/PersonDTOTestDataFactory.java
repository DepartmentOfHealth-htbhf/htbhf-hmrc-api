package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.AddressDTO;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.TestConstants.HOMER_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.TestConstants.HOMER_FORENAME;
import static uk.gov.dhsc.htbhf.TestConstants.HOMER_NINO;
import static uk.gov.dhsc.htbhf.TestConstants.SIMPSON_SURNAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;

public class PersonDTOTestDataFactory {

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
                .dateOfBirth(HOMER_DATE_OF_BIRTH)
                .nino(HOMER_NINO)
                .address(aValidAddress())
                .firstName(HOMER_FORENAME)
                .lastName(SIMPSON_SURNAME);
    }

}
