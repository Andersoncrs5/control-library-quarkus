package org.control.library.dto.users;

import org.control.library.utils.base.BaseDTO;

import java.time.OffsetDateTime;

public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private OffsetDateTime blockedAt;

    public UserDTO() {}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public OffsetDateTime getBlockedAt() {return blockedAt;}
    public void setBlockedAt(OffsetDateTime blockedAt) {this.blockedAt = blockedAt;}
}
