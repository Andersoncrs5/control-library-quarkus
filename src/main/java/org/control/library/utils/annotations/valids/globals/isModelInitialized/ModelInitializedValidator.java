package org.control.library.utils.annotations.valids.globals.isModelInitialized;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.control.library.utils.base.BaseModel;

@ApplicationScoped
public class ModelInitializedValidator implements ConstraintValidator<IsModelInitialized, BaseModel> {

    private static final long MIN_SNOWFLAKE_VALUE = 0L;

    @Override
    public boolean isValid(BaseModel value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Long id = value.getId();

        return id != null && id >= MIN_SNOWFLAKE_VALUE;
    }
}
