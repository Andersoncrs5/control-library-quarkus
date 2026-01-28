package org.control.library.configs.security.snowflake;

import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
@Unremovable
public class SnowflakeIdGenerator {
    private final long workerId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    @Inject
    public SnowflakeIdGenerator(SnowflakeConfig config) {
        this.workerId = config.workerId();
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards!");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long epoch = 1609459200000L;
        return ((timestamp - epoch) << 22) | (workerId << 12) | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
