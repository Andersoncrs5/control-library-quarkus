package org.control.library.dto.users;

import org.control.library.utils.base.BaseDTO;

import java.time.OffsetDateTime;

public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private OffsetDateTime blockedAt;

    public UserDTO() {
    }
}
