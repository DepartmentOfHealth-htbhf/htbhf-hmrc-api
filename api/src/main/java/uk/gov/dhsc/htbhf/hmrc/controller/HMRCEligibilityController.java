package uk.gov.dhsc.htbhf.hmrc.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dhsc.htbhf.hmrc.converter.EligibilityRequestToHMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.service.EligibilityService;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/hmrc/eligibility")
@AllArgsConstructor
@Slf4j
@Api(description = "Endpoints for dealing with HMRC Eligibility requests for Healthy Start.")
public class HMRCEligibilityController {

    private final EligibilityService eligibilityService;
    private final EligibilityRequestToHMRCEligibilityRequest converter;

    @PostMapping
    @ApiOperation("Retrieve the eligibility of a person for Healthy Start based on HMRC's opinion of their income "
            + "and the fact that they're getting Child Tax Credits")
    @ApiResponses({@ApiResponse(code = 200, message = "The person's eligibility for Healthy Start from HMRC's point of view",
            response = EligibilityResponse.class)})
    public EligibilityResponse getBenefits(@RequestBody
                                           @Valid
                                           @ApiParam("The eligibility request for HMRC for Healthy Start")
                                           EligibilityRequest eligibilityRequest) {
        log.debug("Received HMRC eligibility request");

        HMRCEligibilityRequest request = converter.convert(eligibilityRequest);

        return eligibilityService.checkEligibility(request);
    }
}
