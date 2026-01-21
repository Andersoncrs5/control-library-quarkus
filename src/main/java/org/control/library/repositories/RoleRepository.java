package org.control.library.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotBlank;
import org.control.library.models.RoleModel;

import java.util.Optional;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<RoleModel> {

    public boolean existsByName(@NotBlank String name) {
        return find("LOWER(r.name) = LOWER(?1)", name)
                .firstResultOptional()
                .isPresent();
    }

    public Optional<RoleModel> findByName(@NotBlank String name) {
        return find("LOWER(r.name) = LOWER(?1)", name)
                .firstResultOptional();
    }

}
