package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.entity.User;
import com.springboot.practicebank.validation.PasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void initialize(PasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String pass, ConstraintValidatorContext constraintValidatorContext) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return false;
        }

        Object principal = authentication.getPrincipal();

        User user = (User) principal;

        if(passwordEncoder.matches(pass, user.getPassword())){
            return true;
        }
        return false;
    }










    /*
    @Override
    public void initialize(PasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String pass, ConstraintValidatorContext constraintValidatorContext) {

        if(pass == null || pass.isBlank() || pass.length() < 8 || pass.length() > 20 ){
            return false;
        }
        return true;
    }

     */
}
