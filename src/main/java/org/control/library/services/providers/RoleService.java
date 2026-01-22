package org.control.library.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import org.control.library.models.RoleModel;
import org.control.library.repositories.RoleRepository;
import org.control.library.services.interfaces.IRoleService;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;
import org.control.library.utils.exception.ModelNotFoundException;

import java.util.Optional;

@ApplicationScoped
public class RoleService implements IRoleService {

    @Inject
    private RoleRepository repository;

    @Override
    public Boolean existsByName(@NotBlank String name) {
        return this.repository.existsByName(name);
    }

    @Override
    public RoleModel getByNameSimple(@NotBlank String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ModelNotFoundException("Role not found"));
    }

    @Override
    public Optional<RoleModel> getByName(@NotBlank String name) {
        return repository.findByName(name);
    }

    @Override
    public void delete(@IsModelInitialized RoleModel role) {
        this.repository.delete(role);
    }

    @Override
    public RoleModel create(RoleModel role) {
        this.repository.persist(role);
        return role;
    }

    @Override
    public RoleModel update(@IsModelInitialized RoleModel role) {
        this.repository.persist(role);

        return role;
    }

}
