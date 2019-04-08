package uk.gov.dhsc.htbhf.hmrc.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.AddressDTOTestDataFactory.anAddressWithAddressLine1;
import static uk.gov.dhsc.htbhf.hmrc.testhelper.PersonDTOTestDataFactory.*;

class PersonDTOTest extends AbstractValidationTest {

    @Test
    void shouldSuccessfullyValidateAValidPerson() {
        //Given
        PersonDTO person = aValidPerson();
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailValidationWithNoForename() {
        //Given
        PersonDTO person = aPersonWithFirstName(null);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "firstName");
    }

    @Test
    void shouldFailValidationWithNoSurname() {
        //Given
        PersonDTO person = aPersonWithLastName(null);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "lastName");
    }

    @Test
    void shouldFailValidationWithNoNino() {
        //Given
        PersonDTO person = aPersonWithNino(null);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "nino");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab123", "", "YYHU456781", "Y*U", "888888888", "ABCDEFGHI", "ZQQ123456CZ", "QQ123456T", "QQ 12 34 56 D"})
    void shouldFailValidationWithInvalidNino(String nino) {
        //Given
        PersonDTO person = aPersonWithNino(nino);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must match \"[a-zA-Z]{2}\\d{6}[a-dA-D]\"", "nino");
    }

    @Test
    void shouldFailValidationWithNoDateOfBirth() {
        //Given
        PersonDTO person = aPersonWithDateOfBirth(null);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "dateOfBirth");
    }

    @Test
    void shouldFailValidationWithDateOfBirthInFuture() {
        //Given
        LocalDate dateInFuture = LocalDate.now().plusMonths(1);
        PersonDTO person = aPersonWithDateOfBirth(dateInFuture);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must be a past date", "dateOfBirth");
    }

    @Test
    void shouldFailValidationWithNoAddress() {
        //Given
        PersonDTO person = aPersonWithAddress(null);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "address");
    }

    @Test
    void shouldFailValidationWithInvalidAddress() {
        //Given
        AddressDTO invalidAddress = anAddressWithAddressLine1(null);
        PersonDTO person = aPersonWithAddress(invalidAddress);
        //When
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        //Then
        assertThat(violations).hasSingleConstraintViolation("must not be null", "address.addressLine1");
    }
}
