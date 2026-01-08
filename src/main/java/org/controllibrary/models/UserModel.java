package org.controllibrary.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.controllibrary.utils.base.BaseModel;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email")
})
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel {

    @Column(name = "username", length = 200, nullable = false)
    private String username;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private Boolean active = true;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

}
