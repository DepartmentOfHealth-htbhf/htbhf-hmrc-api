package uk.gov.dhsc.htbhf.hmrc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class HMRCEligibilityRequest {

    @JsonProperty("person")
    private HMRCPersonDTO person;

    @JsonProperty("ctcAnnualIncomeThreshold")
    private final BigDecimal ctcAnnualIncomeThreshold;

    @JsonProperty("eligibleStartDate")
    private final LocalDate eligibleStartDate;

    @JsonProperty("eligibleEndDate")
    private final LocalDate eligibleEndDate;
}
