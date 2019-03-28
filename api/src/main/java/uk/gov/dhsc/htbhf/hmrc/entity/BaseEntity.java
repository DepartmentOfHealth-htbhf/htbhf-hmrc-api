package uk.gov.dhsc.htbhf.hmrc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

    @Id
    @Access(AccessType.PROPERTY)
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Adding a custom getter for the id so that we can compare an entity before and after its initial
     * persistence and they will be the same.
     *
     * @return The id for the entity.
     */
    public UUID getId() {
        if (id == null) {
            this.id = UUID.randomUUID();
        }
        return this.id;
    }
}
