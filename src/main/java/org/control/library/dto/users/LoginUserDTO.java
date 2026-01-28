package org.control.library.dto.users;

import org.control.library.utils.annotations.valids.globals.EmailConstraint;

public record LoginUserDTO(
        @EmailConstraint
        String email,

        String password
) {
}
