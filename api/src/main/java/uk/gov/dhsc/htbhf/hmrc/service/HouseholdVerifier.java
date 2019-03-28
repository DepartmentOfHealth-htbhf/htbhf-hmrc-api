package uk.gov.dhsc.htbhf.hmrc.service;

import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.stereotype.Service;
import uk.gov.dhsc.htbhf.hmrc.entity.Adult;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.PersonDTO;

@Service
public class HouseholdVerifier {

    public Boolean detailsMatch(Household household, PersonDTO person) {
        return household.getAdults().stream()
                .anyMatch(adult -> adultMatchesPerson(adult, person));
    }

    private boolean adultMatchesPerson(Adult adult, PersonDTO person) {
        return areEqual(adult.getSurname(), person.getSurname())
                && firstSixCharacterMatch(person.getAddress().getAddressLine1(), adult.getAddressLine1())
                && areEqualIgnoringWhitespace(adult.getPostcode(), person.getAddress().getPostcode());
    }

    private boolean firstSixCharacterMatch(String s1, String s2) {
        return areEqual(StringUtils.left(s1, 6), StringUtils.left(s2, 6));
    }

    private Boolean areEqualIgnoringWhitespace(String s1, String s2) {
        return areEqual(s1.replaceAll("\\s+", ""), s2.replaceAll("\\s+", ""));
    }

    private Boolean areEqual(String s1, String s2) {
        return s1.trim().equalsIgnoreCase(s2.trim());
    }
}
