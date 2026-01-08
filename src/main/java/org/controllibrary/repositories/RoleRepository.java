package org.controllibrary.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotBlank;
import org.controllibrary.models.RoleModel;

@ApplicationScoped
public class RoleRepository implements PanacheRepositoryBase<RoleModel, Long> {

    public Uni<RoleModel> findByName(@NotBlank String name) {
        return find("name LIKE ?1", "%" + name + "%")
                .firstResult();
    }

    public Uni<Boolean> existsByName(@NotBlank String name) {
        return find("name LIKE ?1", "%" + name + "%")
                .count()
                .onItem()
                .transform(x -> x > 0L);
    }

}
