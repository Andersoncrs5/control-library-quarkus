package org.controllibrary.services.providers;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.models.UserRoleModel;
import org.controllibrary.repositories.UserRoleRepository;
import org.controllibrary.services.interfaces.IUserRoleService;
import org.controllibrary.utils.annotations.validations.isId.IsId;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.util.List;

@ApplicationScoped
public class UserRoleService implements IUserRoleService {

    @Inject
    UserRoleRepository repository;

    @Override
    public Uni<Boolean> existsByUserAndRole(UserModel user, RoleModel role) {
        return this.repository.existsByUserAndRole(user, role);
    }

    @Override
    public Uni<List<RoleModel>> getByAllRolesByUser(UserModel user) {
        return this.repository.findRolesByUser(user);
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

    @Override
    @Transactional
    @Retry(
            maxRetries = 4,
            delay = 1_000,
            maxDuration = 10_000,
            jitter = 500
    )
    public Uni<UserRoleModel> create(UserModel user, RoleModel role) {
        UserRoleModel model = new UserRoleModel();
        model.setUser(user);
        model.setRole(role);

        return this.repository.persist(model);
    }

}
