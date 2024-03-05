package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.model.ChangePasswordRequest;
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

         if(request.getPassword().length() < 8 || request.getPassword().length() > 20 || request.getPassword().isBlank()) {
             return false;
         }
         if(request.getOldPassword().equals(request.getPassword())){
             return false;
         }
         return request.getPassword().equals(request.getConfirmPassword());


    }
}
