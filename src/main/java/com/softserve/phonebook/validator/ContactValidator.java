package com.softserve.phonebook.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.softserve.phonebook.api.Contact;

public class ContactValidator implements ConstraintValidator<Validate, Contact> {

    private static final String PATTERN_FOR_NAME = "^[а-яА-ЯёЁіІєЄїЇa-zA-Z0-9\\-]{2,254}$";

    private static final String PATTERN_FOR_PHONE = "\\d{3}-\\d{7}";

    private static final String INVALID_CHARACTERS_OR_EMPTY_FIELD = "Invalid characters in field or field is empty (min:1-max:254).";

    private static final String INVALID_CHARACTERS_OR_EMPTY_FIELD_PHONE = "Invalid characters in field or field is empty (min:1-max:30).";

    @Override
    public void initialize(final Validate constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Contact contact, final ConstraintValidatorContext context) {
        boolean isFirstNameValid = isFirstNameValid(contact);
        boolean isLastNameValid = isLastNameValid(contact);
        boolean isPhoneValid = isPhoneValid(contact);
        printErrorMessages(isFirstNameValid, isLastNameValid, isPhoneValid, context);
        return isFirstNameValid && isLastNameValid && isPhoneValid;
    }

    private boolean isFirstNameValid(final Contact contact) {
        return contact.getFirstName().trim().matches(PATTERN_FOR_NAME);
    }

    private boolean isLastNameValid(final Contact contact) {
        return contact.getLastName().trim().matches(PATTERN_FOR_NAME);
    }

    private boolean isPhoneValid(final Contact contact) {
        return contact.getPhone().trim().matches(PATTERN_FOR_PHONE);
    }

    private void errorMessage(final String field, final String message, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation();
    }

    private void printErrorMessages(final boolean isFirstNameValid, final boolean isLastNameValid,
            final boolean isPhoneValid, final ConstraintValidatorContext context) {
        if (!isFirstNameValid) {
            errorMessage("firstName", INVALID_CHARACTERS_OR_EMPTY_FIELD, context);
        }
        if (!isLastNameValid) {
            errorMessage("lastName", INVALID_CHARACTERS_OR_EMPTY_FIELD, context);
        }
        if (!isPhoneValid) {
            errorMessage("phone", INVALID_CHARACTERS_OR_EMPTY_FIELD_PHONE, context);
        }
       
    }

}
