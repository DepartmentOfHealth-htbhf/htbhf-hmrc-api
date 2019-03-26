package uk.gov.dhsc.htbhf.hmrc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

/**
 * The possible states that a claim can be in according to the HMRC.
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
@ApiModel(description = "The eligibility status according to HMRC")
public enum EligibilityStatus {

    ELIGIBLE,
    INELIGIBLE,
    PENDING,
    NOMATCH
}
