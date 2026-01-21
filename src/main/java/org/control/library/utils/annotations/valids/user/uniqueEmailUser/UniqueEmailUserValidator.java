package org.control.library.utils.annotations.valids.user.uniqueEmailUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.control.library.repositories.UserRepository;

@ApplicationScoped
public class UniqueEmailUserValidator implements ConstraintValidator<UniqueEmailUser, String> {

    @Inject
    UserRepository repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }

        return !repository.existsByEmail(email);
    }
}