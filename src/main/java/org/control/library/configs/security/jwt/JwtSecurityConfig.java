package org.control.library.configs.security.jwt;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "security")
public interface JwtSecurityConfig {

    Jwt jwt();

    Exp exp();

    Password password();

    interface Jwt {
        String secret();
        @WithDefault("USER")
        String group();
    }

    interface Exp {
        @WithName("token")
        int tokenHours();

        @WithName("refresh")
        int refreshHours();
    }

    interface Password {
        Argon argon();

        interface Argon {
            int memory();
            int iterations();
            int parallelism();
        }
    }
}