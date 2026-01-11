package org.controllibrary.dtos.users;

import org.controllibrary.utils.annotations.validations.emailConstraint.EmailConstraint;

public record CreateUserDTO(
        String username,

        @EmailConstraint
        String email,

        String password
) {
}
