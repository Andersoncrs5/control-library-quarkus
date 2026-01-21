package org.control.library.configs.security.appInfo;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import java.util.Optional;

@ConfigMapping(prefix = "quarkus.details")
public interface AppDetailsConfig {

    @WithName("domain")
    String domain();

    @WithDefault("Control Library API")
    String name();

    @WithDefault("1.0.0")
    String version();

    @WithDefault("development")
    String environment();

    Optional<String> contactEmail();
}
