package org.control.library.services.interfaces;

import org.control.library.dto.users.CreateUserDTO;
import org.control.library.dto.users.UpdateUserDTO;
import org.control.library.models.UserModel;
import org.control.library.utils.annotations.valids.globals.EmailConstraint;
import org.control.library.utils.annotations.valids.globals.IsId;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.Optional;

public interface IUserService {
    Optional<UserModel> getByEmail(@EmailConstraint String email);
    Optional<UserModel> getById(@IsId Long id);
    UserModel getByIdSimple(@IsId Long id);
    void delete(@IsModelInitialized UserModel user);
    UserModel create(CreateUserDTO dto);
    UserModel update(UpdateUserDTO dto, @IsModelInitialized UserModel user);
    UserModel update(@IsModelInitialized UserModel user);
}
