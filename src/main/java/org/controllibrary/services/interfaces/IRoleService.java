package org.controllibrary.services.interfaces;

import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotBlank;
import org.controllibrary.models.RoleModel;
import org.controllibrary.utils.annotations.validations.isId.IsId;

public interface IRoleService {
    Uni<RoleModel> findById(@IsId Long id);
    Uni<Boolean> existsByName(@NotBlank String name);
    Uni<RoleModel> findByName(@NotBlank String name);
    Uni<Boolean> delete(@IsId Long id);
    Uni<RoleModel> createOrUpdate(RoleModel role);
}
