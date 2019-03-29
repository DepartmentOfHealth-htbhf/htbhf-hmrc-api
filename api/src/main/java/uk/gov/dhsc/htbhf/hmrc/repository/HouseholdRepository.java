package uk.gov.dhsc.htbhf.hmrc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface HouseholdRepository extends CrudRepository<Household, UUID> {

    /**
     * Return all households containing an adult with the given nino. These are ordered by fileImportNumber in descending order.
     * @param nino The nino to check against
     * @return A stream containing all households found
     */
    @Query("SELECT household FROM Household household INNER JOIN FETCH household.adults adult "
            + "WHERE adult.nino = :nino ORDER BY household.fileImportNumber DESC")
    Stream<Household> findAllHouseholdsByAdultWithNino(@Param("nino") String nino);

    /**
     * Finds a household containing an adult with a matching nino. The household with the highest fileImportNumber
     * (most recent version) is the one returned.
     * @param nino The nino to check against
     * @return An Optional containing a household if found
     */
    @Transactional(readOnly = true)
    default Optional<Household> findHouseholdByAdultWithNino(String nino) {
        Stream<Household> households = findAllHouseholdsByAdultWithNino(nino);
        return households.findFirst();
    }
}
