package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCPersonDTO;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;

public class HMRCPersonDTOTestDataFactory {
    private static final LocalDate DOB = LocalDate.parse("1985-12-31");
    private static final String NINO = "EB123456C";
    private static final String FORENAME = "Lisa";
    private static final String SURNAME = "Simpson";

    public static HMRCPersonDTO aValidHMRCPerson() {
        return buildDefaultHMRCPerson().build();
    }

    private static HMRCPersonDTO.HMRCPersonDTOBuilder buildDefaultHMRCPerson() {
        return HMRCPersonDTO.builder()
                .dateOfBirth(DOB)
                .nino(NINO)
                .address(aValidAddress())
                .forename(FORENAME)
                .surname(SURNAME);
    }
}
