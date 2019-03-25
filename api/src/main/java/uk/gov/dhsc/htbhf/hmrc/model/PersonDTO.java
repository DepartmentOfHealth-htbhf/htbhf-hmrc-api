package uk.gov.dhsc.htbhf.hmrc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "The person who we are checking eligibility for")
public class PersonDTO {

    @NotNull
    @JsonProperty("forename")
    @ApiModelProperty(notes = "The person's forename", example = "Lisa")
    private final String forename;

    @NotNull
    @JsonProperty("surname")
    @ApiModelProperty(notes = "The person's surname", example = "Simpson")
    private final String surname;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{2}\\d{6}[a-dA-D]")
    @JsonProperty("nino")
    @ApiModelProperty(notes = "National Insurance number", example = "QQ123456C")
    private final String nino;

    @NotNull
    @Past
    @JsonProperty("dateOfBirth")
    @ApiModelProperty(notes = "The date of birth, in the format YYYY-MM-DD", example = "1985-12-30")
    private final LocalDate dateOfBirth;

    @NotNull
    @Valid
    @JsonProperty("address")
    @ApiModelProperty(notes = "The person's address")
    private final AddressDTO address;
}
