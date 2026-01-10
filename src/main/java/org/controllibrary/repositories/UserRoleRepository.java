package org.controllibrary.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.models.UserRoleModel;

import java.util.List;

@ApplicationScoped
public class UserRoleRepository implements PanacheRepositoryBase<UserRoleModel, Long> {

    public Uni<List<RoleModel>> findRolesByUser(UserModel user) {
        return find("user", user)
                .project(RoleModel.class)
                .list();
    }

    public Uni<Boolean> existsByUserAndRole(UserModel user, RoleModel role) {
        return count("user = ?1 and role = ?2", user, role)
                .onItem().transform(count -> count > 0);
    }

}
