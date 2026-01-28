package org.control.library.models;

import jakarta.persistence.*;
import org.control.library.utils.base.BaseModel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(
    name = "users",
    indexes = {
            @Index(name = "idx_username", columnList = "username"),
            @Index(name = "idx_email", columnList = "email")
    }
)
public class UserModel extends BaseModel {

    @Column(name = "username", length = 200, nullable = false)
    private String username;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String refreshToken;

    private Boolean active = true;

    private short attemptsLogin = 0;

    @Column(name = "blocked_at")
    private OffsetDateTime blockedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleModel> roles = new ArrayList<>();

    public String getRefreshToken() {return refreshToken;}
    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public OffsetDateTime getBlockedAt() {
        return blockedAt;
    }
    public void setBlockedAt(OffsetDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }
    public short getAttemptsLogin() {
        return attemptsLogin;
    }
    public void setAttemptsLogin(short attemptsLogin) {
        this.attemptsLogin = attemptsLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(username, userModel.username) && Objects.equals(email, userModel.email) && Objects.equals(active, userModel.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }


}
