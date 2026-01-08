package org.controllibrary.dtos.users;

public record UpdateUserDTO(
        String username,
        String email,
        String password
) {
}