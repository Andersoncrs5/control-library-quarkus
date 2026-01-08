package org.controllibrary.services.providers;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.repositories.RoleRepository;
import org.controllibrary.services.interfaces.IRoleService;
import org.controllibrary.utils.annotations.validations.isId.IsId;
import org.controllibrary.utils.exceptions.ModelNotFoundException;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class RoleService implements IRoleService {

    @Inject
    RoleRepository repository;

    @Override
    public Uni<RoleModel> findById(@IsId Long id) {
        return this.repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new ModelNotFoundException("Role not found"));
    }

    @Override
    public Uni<RoleModel> findByName(@NotBlank String name) {
        return this.repository.findByName(name)
                .onItem()
                .ifNull()
                .failWith(() -> new ModelNotFoundException("Role not found"));
    }

    @Override
    public Uni<Boolean> existsByName(@NotBlank String name) {
        return this.repository.existsByName(name);
    }

    @Override
    @Transactional
    @Retry(
            maxRetries = 6,
            delay = 800,
            maxDuration = 10_000,
            jitter = 500
    )
    public Uni<RoleModel> createOrUpdate(RoleModel role) {
        return this.repository.persist(role);
    }

    @Override
    @Transactional
    @Retry(
            maxRetries = 5,
            delay = 1_000,
            maxDuration = 10_000,
            jitter = 500
    )
    public Uni<Boolean> delete(@IsId Long id) {
        return this.repository.deleteById(id);
    }

}
