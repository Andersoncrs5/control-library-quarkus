package org.control.library.utils.annotations.valids.globals;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@NotBlank(message = "{validation.email.notblank}")
@Email(message = "{validation.email.format}")
@Size(min = 5, max = 150, message = "{validation.email.size}")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface EmailConstraint {

    String message() default "Email invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}