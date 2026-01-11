package org.controllibrary.dtos.users;

import org.controllibrary.utils.annotations.validations.emailConstraint.EmailConstraint;

public record LoginUserDTO(

        @EmailConstraint
        String email,

        String password
) {
}
