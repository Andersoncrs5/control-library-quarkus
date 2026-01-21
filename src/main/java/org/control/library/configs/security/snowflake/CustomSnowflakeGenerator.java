package org.control.library.configs.security.snowflake;

import io.quarkus.arc.Arc;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomSnowflakeGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return Arc.container().instance(SnowflakeIdGenerator.class).get().nextId();
    }
}
