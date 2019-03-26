package uk.gov.dhsc.htbhf.hmrc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "The eligibility request for Child Tax Credit")
public class EligibilityRequest {

    @NotNull
    @Valid
    @JsonProperty("person")
    @ApiModelProperty(notes = "Details of the person")
    private PersonDTO person;

    @NotNull
    @JsonProperty("ctcAnnualIncomeThreshold")
    @ApiModelProperty(notes = "The annual income threshold for Child Tax Credit", example = "11000")
    private final BigDecimal ctcAnnualIncomeThreshold;

    @NotNull
    @JsonProperty("eligibleStartDate")
    @ApiModelProperty(notes = "The start date for eligibility", example = "2019-01-01")
    private final LocalDate eligibleStartDate;

    @NotNull
    @JsonProperty("eligibleEndDate")
    @ApiModelProperty(notes = "The end date for eligibility", example = "2019-02-01")
    private final LocalDate eligibleEndDate;
}
