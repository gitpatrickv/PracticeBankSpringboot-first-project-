package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.validation.AgeValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgeValidator implements ConstraintValidator<AgeValid, Integer> {

    private Integer below;
    private Integer above;
    String message;
    @Override
    public void initialize(AgeValid ageValid) {

        this.below = ageValid.below();
        this.above = ageValid.above();
        this.message = ageValid.message();
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        if(age == null){
            return false;
        }
        if(age < below || age > above){
            return false;
        }
        return true;


    }
}
