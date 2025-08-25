package org.example.contactmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComputerCenterEmailValidator implements ConstraintValidator<ValidComputerCenterEmail, String> {

    private static final String EMAIL_REGEX = "^[^\\s@]+@computacenter\\.com$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) return false;

        return email.matches(EMAIL_REGEX);
    }
}
