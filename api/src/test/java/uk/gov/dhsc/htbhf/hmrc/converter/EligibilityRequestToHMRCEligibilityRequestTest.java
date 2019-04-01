package uk.gov.dhsc.htbhf.hmrc.converter;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.hmrc.model.EligibilityRequest;
import uk.gov.dhsc.htbhf.hmrc.model.HMRCEligibilityRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestFactory.anEligibilityRequest;

public class EligibilityRequestToHMRCEligibilityRequestTest {
    private EligibilityRequestToHMRCEligibilityRequest converter = new EligibilityRequestToHMRCEligibilityRequest();

    @Test
    void shouldConvertRequest() {
        EligibilityRequest request = anEligibilityRequest();

        HMRCEligibilityRequest result = converter.convert(request);

        assertThat(result.getEligibleStartDate()).isEqualTo(request.getEligibleStartDate());
        assertThat(result.getEligibleEndDate()).isEqualTo(request.getEligibleEndDate());
        assertThat(result.getCtcAnnualIncomeThreshold()).isEqualTo(request.getCtcAnnualIncomeThreshold());
        assertThat(result.getPerson().getAddress()).isEqualTo(request.getPerson().getAddress());
        assertThat(result.getPerson().getDateOfBirth()).isEqualTo(request.getPerson().getDateOfBirth());
        assertThat(result.getPerson().getForename()).isEqualTo(request.getPerson().getFirstName());
        assertThat(result.getPerson().getSurname()).isEqualTo(request.getPerson().getLastName());
        assertThat(result.getPerson().getNino()).isEqualTo(request.getPerson().getNino());
    }
}
