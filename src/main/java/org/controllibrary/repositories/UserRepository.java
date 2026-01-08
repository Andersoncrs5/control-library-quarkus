package org.controllibrary.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.controllibrary.models.UserModel;
import org.controllibrary.utils.annotations.validations.emailConstraint.EmailConstraint;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserModel, Long> {

    public Uni<UserModel> findByEmail(@EmailConstraint String email) {
        return find("email", email).firstResult();
    }

    public Uni<Boolean> existsByEmail(@EmailConstraint String email) {
        return find("email", email)
                .count()
                .onItem().transform(count -> count > 0L);
    }

}
