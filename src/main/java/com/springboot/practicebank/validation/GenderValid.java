package com.springboot.practicebank.validation;
import com.springboot.practicebank.validation.Impl.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderValid {

    String message() default "{gender}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
