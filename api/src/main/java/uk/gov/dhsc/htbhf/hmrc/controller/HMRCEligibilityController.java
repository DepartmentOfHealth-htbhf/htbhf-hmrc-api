package uk.gov.dhsc.htbhf.hmrc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.service.EligibilityService;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/hmrc/eligibility")
@AllArgsConstructor
@Slf4j
@Api(description = "Endpoints for dealing with HMRC Eligibility requests.")
public class HMRCEligibilityController {

    private final EligibilityService eligibilityService;

    @PostMapping
    @ApiOperation("Retrieve the eligibility of a person for Child Tax Credits")
    @ApiResponses({@ApiResponse(code = 200, message = "The person's eligibility for Child Tax Credit", response = EligibilityResponse.class)})
    public EligibilityResponse getBenefits(@RequestBody
                                           @Valid
                                           @ApiParam("The eligibility request for Child Tax Credit")
                                           EligibilityRequest eligibilityRequest) {
        log.debug("Received eligibility request");
        return eligibilityService.checkEligibility(eligibilityRequest);
    }
}
