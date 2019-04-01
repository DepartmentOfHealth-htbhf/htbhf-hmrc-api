package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.AddressDTO;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;

public class PersonDTOTestFactory {

    private static final LocalDate DOB = LocalDate.parse("1985-12-31");
    private static final String NINO = "EB123456C";
    private static final String FORENAME = "Lisa";
    private static final String SURNAME = "Simpson";

    public static PersonDTO aValidPerson() {
        return buildDefaultPerson().nino("AE000000C").build();
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
                .dateOfBirth(DOB)
                .nino(NINO)
                .address(aValidAddress())
                .firstName(FORENAME)
                .lastName(SURNAME);
    }

}
