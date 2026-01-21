package org.control.library.configs.security.snowflake;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "quarkus.snowflake")
public interface SnowflakeConfig {
    @WithDefault("1")
    long workerId();
}