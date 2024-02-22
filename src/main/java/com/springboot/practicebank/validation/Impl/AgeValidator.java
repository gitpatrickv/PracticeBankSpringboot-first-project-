package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.validation.AgeValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgeValidator implements ConstraintValidator<AgeValid, Integer> {


    @Override
    public void initialize(AgeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        if(age == null){
            return false;
        }
        if(age < 18 || age > 100){
            return false;
        }
        return true;


    }
}
