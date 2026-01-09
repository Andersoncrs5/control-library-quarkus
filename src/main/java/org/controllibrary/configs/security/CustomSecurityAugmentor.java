package org.controllibrary.configs.security;

import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.controllibrary.repositories.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomSecurityAugmentor implements SecurityIdentityAugmentor {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        if (identity.isAnonymous()) {
            return Uni.createFrom().item(identity);
        }

        String email = identity.getPrincipal().getName();

        if (email == null || email.isBlank()) throw new UnauthorizedException();

        return userRepository.findByEmail(email)
                .onItem().ifNull().failWith(() -> new UnauthorizedException("User not found"))
                .onItem().transform(user -> {

                    Set<String> roles = user.getRoles()
                            .stream()
                            .map(x -> x.getRole().getName())
                            .collect(Collectors.toSet());

                    return QuarkusSecurityIdentity.builder(identity)
                            .addAttribute("userId", user.getId())
                            .addAttribute("email", user.getEmail())
                            .addAttribute("active", user.getActive())
                            .addAttribute("blockedAt", user.getBlockedAt())
                            .addRoles(roles)
                            .build();
                });
    }

}
