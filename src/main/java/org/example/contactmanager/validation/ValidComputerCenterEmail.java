package org.example.contactmanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ComputerCenterEmailValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER})
//TODO check
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidComputerCenterEmail {
    String message() default "Invalid domain email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
