package uk.gov.dhsc.htbhf.hmrc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityResponse;


@Service
public class EligibilityService {

    private static final String ENDPOINT = "/v1/hmrc/benefits";
    private final String uri;
    private final RestTemplate restTemplate;

    public EligibilityService(@Value("${hmrc.base-uri}") String baseUri,
                              RestTemplate restTemplate) {
        this.uri = baseUri + ENDPOINT;
        this.restTemplate = restTemplate;
    }

    public EligibilityResponse checkEligibility(EligibilityRequest eligibilityRequest) {
        var response = restTemplate.postForEntity(uri, eligibilityRequest, EligibilityResponse.class);
        return response.getBody();
    }
}
