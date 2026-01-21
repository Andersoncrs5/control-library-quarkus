package org.control.library.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.models.UserRoleModel;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRoleRepository implements PanacheRepository<UserRoleModel> {

    public Boolean existsByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
            ) {
        return count("user = ?1 and role = ?2", user, role) > 0;
    }

    public List<UserRoleModel> findRolesByUser(
            @IsModelInitialized UserModel user
    ) {
        return find("user", user).list();
    }

    public Optional<UserRoleModel> findByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
            ) {

        return find("user = ?1 and role = ?2", user, role).firstResultOptional();
    }

}
