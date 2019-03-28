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
@Table(name = "hmrc_ctc_adult")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SuppressWarnings("PMD.TooManyFields")
public class Adult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hmrc_ctc_household_id", nullable = false)
    private Household household;

    @Size(max = 9)
    @Column(name = "nino")
    private String nino;

    @Column(name = "award_date")
    private LocalDate awardDate;

    @Size(max = 35)
    @Column(name = "surname")
    private String surname;

    @Size(max = 35)
    @Column(name = "first_forename")
    private String firstForeName;

    @Size(max = 35)
    @Column(name = "second_forename")
    private String secondForeName;

    @Size(max = 35)
    @Column(name = "initials")
    private String initials;

    @Size(max = 35)
    @Column(name = "title")
    private String title;

    @Size(max = 35)
    @Column(name = "other_title")
    private String otherTitle;

    @Size(max = 35)
    @Column(name = "additional_address_information")
    private String additionalAddressInformation;

    @Size(max = 35)
    @Column(name = "address_line_1")
    private String addressLine1;

    @Size(max = 35)
    @Column(name = "address_line_2")
    private String addressLine2;

    @Size(max = 35)
    @Column(name = "address_line_3")
    private String addressLine3;

    @Size(max = 35)
    @Column(name = "address_line_4")
    private String addressLine4;

    @Size(max = 35)
    @Column(name = "address_line_5")
    private String addressLine5;

    @Size(max = 8)
    @Column(name = "address_postcode")
    private String postcode;

    @Column(name = "returned_letter_service")
    private Boolean returnedLetterService;
}
