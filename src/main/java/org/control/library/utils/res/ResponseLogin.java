package org.control.library.utils.res;

import jakarta.validation.constraints.NotBlank;
import org.control.library.dto.users.UserDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record ResponseLogin(
        @NotBlank
        String token,

        OffsetDateTime expToken,

        @NotBlank
        String refreshToken,

        OffsetDateTime expRefreshToken,

        UserDTO user,

        List<String> roles
) {
}
