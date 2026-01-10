package org.controllibrary.models;

import jakarta.persistence.*;
import org.controllibrary.utils.base.BaseModel;

import java.util.Objects;

@Entity
@Table(
        name = "user_roles",
        indexes = {
            @Index(name = "idx_user_id", columnList = "user_id"),
            @Index(name = "idx_role_id", columnList = "role_id"),
        },uniqueConstraints = {
            @UniqueConstraint(name = "uk_user_role", columnNames = {"user_id", "role_id"})
        }
)
public class UserRoleModel extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleModel role;

    public UserModel getUser() {
        return user;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleModel that = (UserRoleModel) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
