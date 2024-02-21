package com.springboot.practicebank.validation;

import com.springboot.practicebank.validation.Impl.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface AgeValid {

    String message() default  "{age.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
