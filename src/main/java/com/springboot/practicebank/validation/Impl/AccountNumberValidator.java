package com.springboot.practicebank.validation.Impl;

import com.springboot.practicebank.repository.UserRepository;
import com.springboot.practicebank.validation.AccountNumberValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountNumberValidator implements ConstraintValidator<AccountNumberValid, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(AccountNumberValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {

        return userRepository.existsByAccountNumber(accountNumber);
    }
}
