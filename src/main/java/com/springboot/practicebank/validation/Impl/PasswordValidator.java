package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.validation.PasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.security.Principal;


public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    private Principal principal;

    @Override
    public void initialize(PasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String pass, ConstraintValidatorContext constraintValidatorContext) {

        if(pass == null || pass.isBlank()||pass.length() < 8 || pass.length() > 20 ){
            return false;
        }
        return true;
    }
}
