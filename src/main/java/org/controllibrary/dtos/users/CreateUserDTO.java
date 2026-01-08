package org.controllibrary.dtos.users;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
