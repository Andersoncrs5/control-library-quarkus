package org.controllibrary.services.providers;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.controllibrary.models.UserModel;
import org.controllibrary.repositories.UserRepository;
import org.controllibrary.services.interfaces.IUserService;
import org.controllibrary.utils.exceptions.ModelNotFoundException;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<UserModel> findById(Long id) {
        return this.userRepository.findById(id).onItem().ifNull().failWith(() -> new ModelNotFoundException("User not found"));
    }

}
