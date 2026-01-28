package org.control.library.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.models.UserRoleModel;
import org.control.library.repositories.UserRoleRepository;
import org.control.library.services.interfaces.IUserRoleService;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRoleService implements IUserRoleService {

    @Inject
    UserRoleRepository repository;

    @Override
    public UserRoleModel create(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    ) {
        UserRoleModel model = new UserRoleModel();
        model.setUser(user);
        model.setRole(role);

        this.repository.persist(model);

        return model;
    }

    @Override
    public Optional<UserRoleModel> findByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    ) {
        return this.repository.findByUserAndRole(user, role);
    }

    @Override
    public List<RoleModel> findAllByUser(@IsModelInitialized UserModel user) {
        List<UserRoleModel> list = this.repository.findRolesByUser(user);

        return list.stream().map(UserRoleModel::getRole).toList();
    }

    @Override
    public void delete(@IsModelInitialized UserRoleModel model) {
        this.repository.delete(model);
    }

    @Override
    public Boolean existsByUserAndRole(
            @IsModelInitialized UserModel user,
            @IsModelInitialized RoleModel role
    ) {
        return this.repository.existsByUserAndRole(user, role);
    }

}
