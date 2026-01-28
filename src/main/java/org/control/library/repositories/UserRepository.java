package org.control.library.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.control.library.models.UserModel;
import org.control.library.utils.annotations.valids.globals.EmailConstraint;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserModel> {

    public Boolean existsByEmail(@EmailConstraint String email) {
        return find("LOWER(email) = LOWER(?1)", email)
                .count() > 0;
    }

    public Optional<UserModel> findByEmail(@EmailConstraint String email) {
        return find("LOWER(email) = LOWER(?1)", email).firstResultOptional();
    }

}
