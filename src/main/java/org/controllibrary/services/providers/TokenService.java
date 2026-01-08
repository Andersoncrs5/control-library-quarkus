package org.controllibrary.services.providers;

import org.controllibrary.services.interfaces.ITokenService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class TokenService implements ITokenService {

    @ConfigProperty(name = "security.jwt.secret")
    private String secret;
    @ConfigProperty(name = "security.exp.token")
    private int expToken;
    @ConfigProperty(name = "security.exp.refresh")
    private int expRefreshToken;



}
