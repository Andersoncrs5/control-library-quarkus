package org.control.library.services.interfaces;

import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.models.UserRoleModel;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.Optional;

public interface IUserRoleService {
    UserRoleModel create(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    );
    Optional<UserRoleModel> findByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    );
    void delete(@IsModelInitialized UserRoleModel model);
    Boolean existsByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    );
}
