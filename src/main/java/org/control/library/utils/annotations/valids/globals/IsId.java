package org.control.library.utils.annotations.valids.globals;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.lang.annotation.*;

@NotNull(message = "The ID cannot be null.")
@Positive(message = "The ID must be a positive number.")
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface IsId {
    String message() default "ID invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
