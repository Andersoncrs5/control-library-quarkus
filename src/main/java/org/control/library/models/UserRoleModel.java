package org.control.library.models;

import jakarta.persistence.*;
import org.control.library.utils.base.BaseModel;

@Entity
@Table(
    name = "user_roles",
    indexes = {
            @Index(name = "idx_user_id", columnList = "user_id"),
            @Index(name = "idx_role_id", columnList = "role_id"),
    },
    uniqueConstraints = {
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

}
