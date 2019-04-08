package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.AddressDTO;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_FORENAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_NINO;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.SIMPSONS_SURNAME;

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
                .dateOfBirth(LISA_DATE_OF_BIRTH)
                .nino(LISA_NINO)
                .address(aValidAddress())
                .firstName(LISA_FORENAME)
                .lastName(SIMPSONS_SURNAME);
    }

}
