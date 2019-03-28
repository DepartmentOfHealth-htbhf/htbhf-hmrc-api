package uk.gov.dhsc.htbhf.hmrc.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "hmrc_ctc_child")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Child extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hmrc_ctc_household_id", nullable = false)
    private Household household;

    @Size(max = 35)
    @Column(name = "first_forename")
    private String firstForeName;

    @Size(max = 35)
    @Column(name = "second_forename")
    private String secondForeName;

    @Size(max = 35)
    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "responsibility_start_date")
    private LocalDate responsibilityStartDate;

    @Column(name = "entitlement_start_date")
    private LocalDate entitlementStartDate;
}
