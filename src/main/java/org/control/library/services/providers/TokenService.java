package org.control.library.services.providers;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.control.library.configs.security.appInfo.AppDetailsConfig;
import org.control.library.configs.security.jwt.JwtSecurityConfig;
import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.services.interfaces.ITokenService;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public String generateToken(@IsModelInitialized UserModel user, List<RoleModel> roles) {
        Set<String> list = roles.stream().map(RoleModel::getName).collect(Collectors.toSet());

        return Jwt.issuer(appDetails.domain())
                .subject(user.getId().toString())
                .upn(user.getEmail())
                .groups(list)
                .claim("type", "token")
                .claim("userId", user.getId())
                .claim("active", user.getActive())
                .claim("jti", UUID.randomUUID().toString())
                .claim("createdAt", OffsetDateTime.now())
                .expiresIn(Duration.ofHours(securityConfig.exp().tokenHours()))
                .sign();
    }

    @Override
    public String generateRefreshToken(@IsModelInitialized UserModel user) {
        return Jwt.issuer(appDetails.domain())
                .subject(user.getEmail())
                .claim("type", "refresh")
                .claim("jti", UUID.randomUUID().toString())
                .claim("createdAt", OffsetDateTime.now())
                .expiresIn(Duration.ofHours(this.securityConfig.exp().refreshHours()))
                .sign();
    }

    @Override
    public Map<String, Object> extractAllClaims(String token) {
        JsonWebToken jwt = parseToken(token);
        Map<String, Object> claims = new HashMap<>();

        for (String name : jwt.getClaimNames()) {
            claims.put(name, jwt.getClaim(name));
        }
        return claims;
    }

    @Override
    public String validateToken(String token) {
        return parseToken(token).getSubject();
    }

    private JsonWebToken parseToken(String token) {
        if (token == null || token.isBlank()) {
            throw new UnauthorizedException("Token is missing");
        }

        try {
            return parser.parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }


}
