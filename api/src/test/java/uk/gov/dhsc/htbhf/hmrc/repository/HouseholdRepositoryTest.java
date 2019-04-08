package uk.gov.dhsc.htbhf.hmrc.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHousehold;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.aHouseholdWithNoAdultsOrChildren;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.HouseholdTestDataFactory.anAdultWithNino;

@DataJpaTest
public class HouseholdRepositoryTest {

    @Autowired
    private HouseholdRepository repository;

    @PersistenceContext
    EntityManager em;

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveHousehold() {
        //Given
        Household household = aHousehold();

        //When
        Household savedHousehold = repository.save(household);

        //Then
        assertThat(savedHousehold.getId()).isEqualTo(household.getId());
        household.getChildren().forEach(child -> assertThat(em.contains(child)));
        household.getAdults().forEach(adult -> assertThat(em.contains(adult)));
        assertThat(savedHousehold).isEqualTo(household);
        assertThat(savedHousehold).isEqualToComparingFieldByFieldRecursively(household);
    }

    @Test
    void shouldFindMostRecentVersionOfHouseholdByNino() {
        String nino = "QQ111111A";
        Household household1Version1 = aHouseholdWithNoAdultsOrChildren().fileImportNumber(1).build().addAdult(anAdultWithNino(nino));
        Household household1Version2 = aHouseholdWithNoAdultsOrChildren().fileImportNumber(2).build().addAdult(anAdultWithNino(nino));
        Household household2Version1 = aHouseholdWithNoAdultsOrChildren().fileImportNumber(1).build().addAdult(anAdultWithNino("QQ222222C"));
        Household household2Version2 = aHouseholdWithNoAdultsOrChildren().fileImportNumber(2).build().addAdult(anAdultWithNino("QQ222222C"));
        repository.save(household1Version1);
        repository.save(household1Version2);
        repository.save(household2Version1);
        repository.save(household2Version2);

        Optional<Household> result = repository.findHouseholdByAdultWithNino(nino);

        assertThat(result).contains(household1Version2);
    }

    @Test
    void shouldFailToFindHouseholdByNino() {
        Household household = aHousehold();
        repository.save(household);

        Optional<Household> result = repository.findHouseholdByAdultWithNino("AB999999C");

        assertThat(result).isEmpty();
    }
}
