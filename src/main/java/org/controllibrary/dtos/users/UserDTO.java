package org.controllibrary.dtos.users;

import java.time.OffsetDateTime;

public record UserDTO(
        Long id,
        String username,
        String email,
        String password,
        Boolean active,
        OffsetDateTime blockedAt,
        Long version,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
