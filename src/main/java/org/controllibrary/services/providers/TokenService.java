package org.controllibrary.services.providers;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.services.interfaces.ITokenService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.jwt.build.Jwt;

import org.eclipse.microprofile.jwt.Claims;
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

    @ConfigProperty(name = "security.jwt.secret")
    private String secret;
    @ConfigProperty(name = "security.jwt.group")
    private String group;
    @ConfigProperty(name = "security.exp.token")
    private int expToken;
    @ConfigProperty(name = "security.exp.refresh")
    private int expRefreshToken;
    @ConfigProperty(name = "spring.details.domain")
    private String domain;

    @Override
    public String generateToken(UserModel user, List<RoleModel> roles) {
        Set<String> roleNames = roles.stream()
                .map(RoleModel::getName)
                .collect(Collectors.toSet());

        return Jwt.issuer(domain)
                .subject(user.getEmail())
                .upn(user.getEmail())
                .groups(roleNames)
                .claim(Claims.full_name.name(), user.getUsername())
                .claim("userId", user.getId())
                .claim("active", user.getActive())
                .claim("jti", UUID.randomUUID().toString())
                .claim("createdAt", OffsetDateTime.now())
                .expiresIn(Duration.ofHours(expToken))
                .sign();
    }

    @Override
    public String generateRefreshToken(UserModel user) {
        return Jwt.issuer(domain)
                .subject(user.getEmail())
                .claim("typ", "Refresh")
                .claim("jti", UUID.randomUUID().toString())
                .claim("createdAt", OffsetDateTime.now())
                .expiresIn(Duration.ofHours(this.expRefreshToken))
                .sign();
    }

    @Override
    public String validateToken(String token) {
        return parseToken(token).getSubject();
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
    public Long getUserIdFromToken() {
        return Long.valueOf(jwt.getClaim("userId").toString());
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
