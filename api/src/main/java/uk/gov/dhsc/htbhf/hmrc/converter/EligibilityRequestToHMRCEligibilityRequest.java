package uk.gov.dhsc.htbhf.hmrc.converter;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCPersonDTO;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

@Component
@AllArgsConstructor
public class EligibilityRequestToHMRCEligibilityRequest {

    public HMRCEligibilityRequest convert(EligibilityRequest eligibilityRequest) {
        return HMRCEligibilityRequest.builder()
                .person(convertPerson(eligibilityRequest.getPerson()))
                .eligibleStartDate(eligibilityRequest.getEligibleStartDate())
                .eligibleEndDate(eligibilityRequest.getEligibleEndDate())
                .ctcAnnualIncomeThreshold(eligibilityRequest.getCtcAnnualIncomeThreshold())
                .build();
    }

    public HMRCPersonDTO convertPerson(PersonDTO person) {
        return HMRCPersonDTO.builder()
                .address(person.getAddress())
                .dateOfBirth(person.getDateOfBirth())
                .forename(person.getFirstName())
                .surname(person.getLastName())
                .nino(person.getNino())
                .build();
    }
}

