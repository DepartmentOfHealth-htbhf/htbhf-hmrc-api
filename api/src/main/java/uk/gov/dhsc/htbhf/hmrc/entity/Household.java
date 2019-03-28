package uk.gov.dhsc.htbhf.hmrc.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import static java.util.Collections.unmodifiableSet;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "hmrc_ctc_household")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Household extends BaseEntity {

    @Size(min = 1, max = 50)
    @Column(name = "household_identifier")
    private String householdIdentifier;

    @Column(name = "file_import_number")
    private Integer fileImportNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "household", orphanRemoval = true)
    @ToString.Exclude
    private final Set<Adult> adults = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "household", orphanRemoval = true)
    @ToString.Exclude
    private final Set<Child> children = new HashSet<>();

    public Household addAdult(Adult adult) {
        adult.setHousehold(this);
        this.adults.add(adult);
        return this;
    }

    public Set<Adult> getAdults() {
        return unmodifiableSet(adults);
    }

    public void setAdults(Set<Adult> adults) {
        this.adults.clear();
        adults.forEach(this::addAdult);
    }

    public Household addChild(Child child) {
        child.setHousehold(this);
        this.children.add(child);
        return this;
    }

    public Set<Child> getChildren() {
        return unmodifiableSet(children);
    }

    public void setChildren(Set<Child> children) {
        this.children.clear();
        children.forEach(this::addChild);
    }
}
