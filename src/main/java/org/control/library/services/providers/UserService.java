package org.control.library.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.control.library.configs.security.CryptoService;
import org.control.library.dto.users.CreateUserDTO;
import org.control.library.dto.users.UpdateUserDTO;
import org.control.library.models.UserModel;
import org.control.library.repositories.UserRepository;
import org.control.library.services.interfaces.IUserService;
import org.control.library.utils.annotations.valids.globals.EmailConstraint;
import org.control.library.utils.annotations.valids.globals.IsId;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;
import org.control.library.utils.exception.ModelNotFoundException;
import org.control.library.utils.exception.NotAuthenticatedException;
import org.control.library.utils.mappers.UserMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    private UserMapper mapper;

    @Inject
    private CryptoService cryptoService;

    @Inject
    private UserRepository repository;

    public Optional<UserModel> getByEmail(@EmailConstraint String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public Optional<UserModel> getById(@IsId Long id) {
        return this.repository.findByIdOptional(id);
    }

    @Override
    public UserModel getByIdSimple(@IsId Long id) {
        return this.repository.findByIdOptional(id)
                .orElseThrow(() -> new ModelNotFoundException("User not found"));
    }

    @Override
    public void delete(@IsModelInitialized UserModel user) {
        this.repository.delete(user);
    }

    @Override
    public UserModel create(CreateUserDTO dto) {
        UserModel userModel = mapper.toModel(dto);

        userModel.setPassword(this.cryptoService.encode(userModel.getPassword()));

        repository.persist(userModel);

        return userModel;
    }

    @Override
    public UserModel update(UpdateUserDTO dto, @IsModelInitialized UserModel user){
        this.mapper.merge(dto, user);

        repository.persist(user);

        return user;
    }

    @Override
    public UserModel update(@IsModelInitialized UserModel user){
        repository.persist(user);

        return user;
    }

    @Override
    public UserModel getByJsonWebToken(JsonWebToken jwt) {
        String sub = jwt.getSubject();
        long userId;

        if (sub == null || sub.isBlank()) {
            throw new NotAuthenticatedException("Token subject is missing");
        }

        try {
            userId = Long.parseLong(sub);

        } catch (NumberFormatException ex) {
            throw new NotAuthenticatedException("Invalid user identity format");
        }

        return this.getById(userId)
                .orElseThrow(() -> new NotAuthenticatedException("User not found"));
    }

}
