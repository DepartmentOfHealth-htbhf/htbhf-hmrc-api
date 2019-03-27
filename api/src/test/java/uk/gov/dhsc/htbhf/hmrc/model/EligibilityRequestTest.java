package uk.gov.dhsc.htbhf.hmrc.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.EligibilityRequestTestFactory.*;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestFactory.aPersonWithNino;

class EligibilityRequestTest extends AbstractValidationTest {

    @Test
    void shouldSuccessfullyValidateAValidEligibilityRequest() {
        //Given
        EligibilityRequest request = anEligibilityRequest();
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailValidationWithNoPerson() {
        //Given
        EligibilityRequest request = anEligibilityRequestWithPerson(null);
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "person");
    }

    @Test
    void shouldFailValidationWithInvalidPerson() {
        //Given
        PersonDTO invalidPerson = aPersonWithNino(null);
        EligibilityRequest request = anEligibilityRequestWithPerson(invalidPerson);
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "person.nino");
    }

    @Test
    void shouldFailValidationWithNoCtcThreshold() {
        //Given
        EligibilityRequest request = anEligibilityRequestWithCtcAnnualIncomeThreshold(null);
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "ctcAnnualIncomeThreshold");
    }

    @Test
    void shouldFailValidationWithEligibilityStartDate() {
        //Given
        EligibilityRequest request = anEligibilityRequestWithEligibileStartDate(null);
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "eligibleStartDate");
    }

    @Test
    void shouldFailValidationWithEligibilityEndDate() {
        //Given
        EligibilityRequest request = anEligibilityRequestWithEligibileEndDate(null);
        //When
        Set<ConstraintViolation<EligibilityRequest>> violations = validator.validate(request);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "eligibleEndDate");
    }

}
