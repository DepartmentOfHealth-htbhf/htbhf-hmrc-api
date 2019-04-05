package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCPersonDTO;

import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_DOB;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_FORENAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_NINO;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.TestConstants.LISA_SURNAME;

public class HMRCPersonDTOTestDataFactory {

    public static HMRCPersonDTO aValidHMRCPerson() {
        return buildDefaultHMRCPerson().build();
    }

    private static HMRCPersonDTO.HMRCPersonDTOBuilder buildDefaultHMRCPerson() {
        return HMRCPersonDTO.builder()
                .dateOfBirth(LISA_DOB)
                .nino(LISA_NINO)
                .address(aValidAddress())
                .forename(LISA_FORENAME)
                .surname(LISA_SURNAME);
    }
}
