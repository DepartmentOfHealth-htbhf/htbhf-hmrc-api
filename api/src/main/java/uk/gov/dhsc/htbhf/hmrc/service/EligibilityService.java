package uk.gov.dhsc.htbhf.hmrc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.hmrc.entity.Household;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.repository.HouseholdRepository;

import java.util.Optional;

import static uk.gov.dhsc.htbhf.hmrc.factory.EligibilityResponseFactory.createEligibilityResponse;
import static uk.gov.dhsc.htbhf.hmrc.model.EligibilityStatus.NOMATCH;


@Service
@Slf4j
public class EligibilityService {

    private static final String ENDPOINT = "/v1/hmrc/benefits";
    private final String uri;
    private final HouseholdRepository repository;
    private final HouseholdVerifier householdVerifier;
    private final RestTemplate restTemplate;

    public EligibilityService(@Value("${hmrc.base-uri}") String baseUri,
                              HouseholdRepository repository,
                              HouseholdVerifier householdVerifier,
                              RestTemplate restTemplate) {
        this.uri = baseUri + ENDPOINT;
        this.repository = repository;
        this.householdVerifier = householdVerifier;
        this.restTemplate = restTemplate;
    }

    public EligibilityResponse checkEligibility(HMRCEligibilityRequest eligibilityRequest) {
        Optional<Household> household = repository.findHouseholdByAdultWithNino(eligibilityRequest.getPerson().getNino());
        if (household.isPresent()) {
            log.debug("Matched CTC household: {}", household.get().getHouseholdIdentifier());
            return getEligibilityResponse(eligibilityRequest, household.get());
        }

        log.debug("No match found in db - calling HMRC to check eligibility");
        return getResponseFromHMRC(eligibilityRequest);
    }

    private EligibilityResponse getEligibilityResponse(HMRCEligibilityRequest eligibilityRequest, Household household) {
        return householdVerifier.detailsMatch(household, eligibilityRequest.getPerson())
                ? createEligibilityResponse(household)
                : EligibilityResponse.builder().eligibilityStatus(NOMATCH).build();
    }

    private EligibilityResponse getResponseFromHMRC(HMRCEligibilityRequest eligibilityRequest) {
        ResponseEntity<EligibilityResponse> response = restTemplate.postForEntity(uri, eligibilityRequest, EligibilityResponse.class);
        return response.getBody();
    }
}
