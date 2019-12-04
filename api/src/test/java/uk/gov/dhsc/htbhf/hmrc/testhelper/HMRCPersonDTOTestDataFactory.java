package uk.gov.dhsc.htbhf.hmrc.testhelper;

import uk.gov.dhsc.htbhf.hmrc.model.HMRCPersonDTO;

import static uk.gov.dhsc.htbhf.TestConstants.HOMER_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.TestConstants.HOMER_FORENAME;
import static uk.gov.dhsc.htbhf.TestConstants.HOMER_NINO_V1;
import static uk.gov.dhsc.htbhf.TestConstants.SIMPSON_SURNAME;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.aValidAddress;

public class HMRCPersonDTOTestDataFactory {

    public static HMRCPersonDTO aValidHMRCPerson() {
        return buildDefaultHMRCPerson().build();
    }

    private static HMRCPersonDTO.HMRCPersonDTOBuilder buildDefaultHMRCPerson() {
        return HMRCPersonDTO.builder()
                .dateOfBirth(HOMER_DATE_OF_BIRTH)
                .nino(HOMER_NINO_V1)
                .address(aValidAddress())
                .forename(HOMER_FORENAME)
                .surname(SIMPSON_SURNAME);
    }
}
