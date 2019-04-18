package uk.gov.dhsc.htbhf.hmrc.factory;

import org.springframework.stereotype.Component;
import uk.gov.dhsc.htbhf.eligibility.model.EligibilityStatus;
import uk.gov.dhsc.htbhf.hmrc.entity.Child;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.ChildDTO;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EligibilityResponseFactory {

    public EligibilityResponse createEligibilityResponse(Household household, EligibilityStatus status) {
        return EligibilityResponse.builder()
                .eligibilityStatus(status)
                .numberOfChildrenUnderOne(getNumberOfChildrenUnderOne(household.getChildren()))
                .numberOfChildrenUnderFour(getNumberOfChildrenUnderFour(household.getChildren()))
                .householdIdentifier(household.getHouseholdIdentifier())
                .children(getChildrenUnderFour(household.getChildren()))
                .build();
    }

    private List<ChildDTO> getChildrenUnderFour(Set<Child> children) {
        return children.stream()
                .filter(child -> isUnderFour(child.getDateOfBirth()))
                .map(child -> ChildDTO.builder().dateOfBirth(child.getDateOfBirth()).build())
                .collect(Collectors.toList());
    }

    private boolean isUnderFour(LocalDate dateOfBirth) {
        return dateOfBirth.isAfter(LocalDate.now().minusYears(4));
    }

    private Integer getNumberOfChildrenUnderOne(Set<? extends Child> children) {
        return getNumberOfChildrenUnderAgeInYears(children, 1);
    }

    private Integer getNumberOfChildrenUnderFour(Set<? extends Child> children) {
        return getNumberOfChildrenUnderAgeInYears(children, 4);
    }

    private Integer getNumberOfChildrenUnderAgeInYears(Set<? extends Child> children, Integer ageInYears) {
        LocalDate pastDate = LocalDate.now().minusYears(ageInYears);
        return Math.toIntExact(children.stream()
                .filter(child -> child.getDateOfBirth().isAfter(pastDate))
                .count());
    }
}
