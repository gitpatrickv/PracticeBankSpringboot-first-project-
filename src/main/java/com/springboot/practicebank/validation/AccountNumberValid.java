package com.springboot.practicebank.validation;

import com.springboot.practicebank.validation.Impl.AccountNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountNumberValidator.class)
public @interface AccountNumberValid {

    String message() default "{account.number.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
