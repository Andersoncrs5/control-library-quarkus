package org.control.library.services.interfaces;

import jakarta.validation.constraints.NotBlank;
import org.control.library.models.RoleModel;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.Optional;

public interface IRoleService {
    Boolean existsByName(@NotBlank String name);
    RoleModel getByNameSimple(@NotBlank String name);
    Optional<RoleModel> getByName(@NotBlank String name);
    void delete(@IsModelInitialized RoleModel role);
    RoleModel create(RoleModel role);
    RoleModel update(@IsModelInitialized RoleModel role);
}
