package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.dto.ChangePasswordRequest;
import com.springboot.practicebank.validation.PasswordMatchValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchValid, Object> {

    @Override
    public void initialize(PasswordMatchValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        ChangePasswordRequest request = (ChangePasswordRequest) object;
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
