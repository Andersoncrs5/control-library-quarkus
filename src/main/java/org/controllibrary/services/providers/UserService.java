package org.controllibrary.services.providers;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.controllibrary.dtos.users.CreateUserDTO;
import org.controllibrary.dtos.users.UpdateUserDTO;
import org.controllibrary.models.UserModel;
import org.controllibrary.repositories.UserRepository;
import org.controllibrary.services.interfaces.IUserService;
import org.controllibrary.utils.annotations.validations.emailConstraint.EmailConstraint;
import org.controllibrary.utils.annotations.validations.isId.IsId;
import org.controllibrary.utils.exceptions.ModelNotFoundException;
import org.controllibrary.utils.mappers.UserMapper;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject UserRepository repository;
    @Inject UserMapper mapper;

    @Override
    public Uni<UserModel> findById(@IsId Long id) {
        return this.repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new ModelNotFoundException("User not found"));
    }

    @Override
    public Uni<Boolean> existsByEmail(@EmailConstraint String email) {
        return this.repository.existsByEmail(email);
    }

    @Retry
    @Override
    @Transactional
    public Uni<Boolean> delete(@IsId Long id) {
        return this.repository.deleteById(id);
    }

    @Retry
    @Override
    @Transactional
    public Uni<UserModel> create(CreateUserDTO dto) {
        UserModel model = this.mapper.toModel(dto);

        return repository.persist(model);
    }

    @Retry
    @Override
    @Transactional
    public Uni<UserModel> update(UserModel user, UpdateUserDTO dto) {
        this.mapper.merge(dto, user);

        return this.repository.persist(user);
    }

}
