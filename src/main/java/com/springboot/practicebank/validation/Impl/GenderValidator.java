package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.validation.GenderValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderValid, String> {
    @Override
    public void initialize(GenderValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String g, ConstraintValidatorContext constraintValidatorContext) {

        //List<String> gender = Arrays.asList("MALE", "FEMALE");
        return "MALE".equalsIgnoreCase(g) || "FEMALE".equalsIgnoreCase(g);
    }
}
