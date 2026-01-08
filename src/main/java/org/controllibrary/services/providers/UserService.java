package org.controllibrary.services.providers;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.controllibrary.models.UserModel;
import org.controllibrary.repositories.UserRepository;
import org.controllibrary.services.interfaces.IUserService;
import org.controllibrary.utils.annotations.validations.isId.IsId;
import org.controllibrary.utils.exceptions.ModelNotFoundException;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<UserModel> findById(@IsId Long id) {
        return this.userRepository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new ModelNotFoundException("User not found"));
    }

    @Override
    @WithTransaction
    public Uni<Boolean> delete(@IsId Long id) {
        return this.userRepository.deleteById(id);
    }

}
