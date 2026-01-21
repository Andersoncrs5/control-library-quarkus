package org.control.library.dto.users;

import org.control.library.utils.annotations.valids.user.uniqueEmailUser.UniqueEmailUser;

public record CreateUserDTO(
        String username,

        @UniqueEmailUser
        String email,

        String password
) {
}
