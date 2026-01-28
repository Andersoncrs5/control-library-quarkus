package org.control.library.controller.provider;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.control.library.configs.jackson.MediaTypes;
import org.control.library.configs.security.CryptoService;
import org.control.library.controller.interfaces.AuthControllerDocs;
import org.control.library.dto.users.CreateUserDTO;
import org.control.library.dto.users.LoginUserDTO;
import org.control.library.dto.users.UserDTO;
import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.services.interfaces.ITokenService;
import org.control.library.services.interfaces.IUserRoleService;
import org.control.library.services.interfaces.IUserService;
import org.control.library.utils.mappers.UserMapper;
import org.control.library.utils.res.ResponseHTTP;
import org.control.library.utils.res.ResponseLogin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Path("/v1/auth")
@Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
        MediaTypes.APPLICATION_YAML
})
@Consumes({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
        MediaTypes.APPLICATION_YAML
})
public class AuthController implements AuthControllerDocs {

    private final IUserService service;
    private final ITokenService tokenService;
    private final IUserRoleService userRoleService;
    private final UserMapper mapper;
    private final CryptoService cryptoService;

    public AuthController(IUserService service, ITokenService tokenService, IUserRoleService userRoleService, UserMapper mapper, CryptoService cryptoService) {
        this.service = service;
        this.tokenService = tokenService;
        this.userRoleService = userRoleService;
        this.mapper = mapper;
        this.cryptoService = cryptoService;
    }

    @Override
    @Transactional
    public Response create(CreateUserDTO dto) {
        UserModel user = service.create(dto);
        List<RoleModel> roles = this.userRoleService.findAllByUser(user);
        UserDTO userDTO = this.mapper.toDTO(user);

        ResponseLogin tokens = this.tokenService.makeTokens(user, roles, userDTO);

        user.setRefreshToken(tokens.refreshToken());
        service.update(user);

        return Response.status(201).entity(new ResponseHTTP<>(
                tokens,
                "User created",
                true
        )).build();
    }

    @Override
    @Transactional
    public Response login(LoginUserDTO dto) {
        UserModel user = this.service.getByEmail(dto.email()).orElse(null);

        if (
                user != null &&
                user.getBlockedAt() != null &&
                user.getBlockedAt().isAfter(OffsetDateTime.now())
        ) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ResponseHTTP<>(
                            null,
                            "Account temporarily locked. Try again later.",
                            false
                    )).build();
        }

        boolean isValid = user != null && cryptoService.verify(dto.password(), user.getPassword());

        if (!isValid) {
            if (user != null) processFailedAttempt(user);

            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new ResponseHTTP<>(
                            null,
                            "Invalid email or password",
                            false
                    )).build();
        }

        user.setAttemptsLogin((short) 0);
        user.setBlockedAt(null);

        List<RoleModel> roles = this.userRoleService.findAllByUser(user);
        ResponseLogin tokens = this.tokenService.makeTokens(user, roles, this.mapper.toDTO(user));

        user.setRefreshToken(tokens.refreshToken());
        this.service.update(user);

        return Response.ok(new ResponseHTTP<>(
                tokens,
                "Login successful",
                true
                )).build();
    }

    private void processFailedAttempt(UserModel user) {
        short attempts = (short) (user.getAttemptsLogin() + 1);
        user.setAttemptsLogin(attempts);

        if (attempts >= 3) user.setBlockedAt(OffsetDateTime.now().plusHours(4));

        this.service.update(user);
    }

}
