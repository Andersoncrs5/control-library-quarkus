package org.controllibrary.services.interfaces;

import io.smallrye.mutiny.Uni;
import org.controllibrary.dtos.users.CreateUserDTO;
import org.controllibrary.dtos.users.UpdateUserDTO;
import org.controllibrary.models.UserModel;
import org.controllibrary.utils.annotations.validations.emailConstraint.EmailConstraint;
import org.controllibrary.utils.annotations.validations.isId.IsId;

public interface IUserService {
    Uni<UserModel> findById(@IsId Long id);
    Uni<Boolean> delete(@IsId Long id);
    Uni<Boolean> existsByEmail(@EmailConstraint String email);
    Uni<UserModel> create(CreateUserDTO dto);
    Uni<UserModel> update(UserModel user, UpdateUserDTO dto);
}
