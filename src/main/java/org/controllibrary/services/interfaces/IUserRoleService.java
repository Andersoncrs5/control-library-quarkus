package org.controllibrary.services.interfaces;

import io.smallrye.mutiny.Uni;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.models.UserRoleModel;
import org.controllibrary.utils.annotations.validations.isId.IsId;

import java.util.List;

public interface IUserRoleService {
    Uni<List<RoleModel>> getByAllRolesByUser(UserModel user);
    Uni<Boolean> delete(@IsId Long id);
    Uni<UserRoleModel> create(UserModel user, RoleModel role);
    Uni<Boolean> existsByUserAndRole(UserModel user, RoleModel role);
}
