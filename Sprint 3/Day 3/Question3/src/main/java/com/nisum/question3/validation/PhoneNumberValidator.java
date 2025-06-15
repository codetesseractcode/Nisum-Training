package com.nisum.question3.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        // Check if the phone number matches the E.164 format (simplified)
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
