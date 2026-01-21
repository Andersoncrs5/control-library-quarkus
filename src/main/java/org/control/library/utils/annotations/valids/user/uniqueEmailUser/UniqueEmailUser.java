package org.control.library.utils.annotations.valids.user.uniqueEmailUser;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.control.library.utils.annotations.valids.globals.EmailConstraint;

import java.lang.annotation.*;

@EmailConstraint
@Documented
@Constraint(validatedBy = UniqueEmailUserValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailUser {
    String message() default "Email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}