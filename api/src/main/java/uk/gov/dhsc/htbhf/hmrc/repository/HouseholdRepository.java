package uk.gov.dhsc.htbhf.hmrc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;

import java.util.UUID;

@Repository
public interface HouseholdRepository extends CrudRepository<Household, UUID> {
}
