package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCPersonDTO;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.*;

public class HMRCPersonDTOTestDataFactory {

    public static HMRCPersonDTO aValidHMRCPerson() {
        return buildDefaultHMRCPerson().build();
    }

    private static HMRCPersonDTO.HMRCPersonDTOBuilder buildDefaultHMRCPerson() {
        return HMRCPersonDTO.builder()
                .dateOfBirth(LISA_DATE_OF_BIRTH)
                .nino(LISA_NINO)
                .address(aValidAddress())
                .forename(LISA_FORENAME)
                .surname(SIMPSONS_SURNAME);
    }
}
