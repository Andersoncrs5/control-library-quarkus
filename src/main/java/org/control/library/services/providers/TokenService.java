package org.control.library.services.providers;

import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.control.library.configs.security.appInfo.AppDetailsConfig;
import org.control.library.configs.security.jwt.JwtSecurityConfig;
import org.control.library.services.interfaces.ITokenService;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class TokenService implements ITokenService {

    @Inject
    JWTParser parser;

    @Inject
    JsonWebToken jwt;

    @Inject
    AppDetailsConfig appDetails;

    @Inject
    JwtSecurityConfig securityConfig;



}
