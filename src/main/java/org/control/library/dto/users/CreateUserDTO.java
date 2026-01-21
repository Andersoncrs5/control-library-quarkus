package org.control.library.dto.users;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
