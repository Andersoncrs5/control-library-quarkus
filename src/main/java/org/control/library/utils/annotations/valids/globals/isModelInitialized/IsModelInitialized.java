package org.control.library.utils.annotations.valids.globals.isModelInitialized;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ModelInitializedValidator.class)
@Documented
public @interface IsModelInitialized {
    String message() default "The entity model must be initialized with a valid ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}