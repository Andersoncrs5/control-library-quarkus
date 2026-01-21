package org.control.library.models;


import jakarta.persistence.*;
import org.control.library.utils.base.BaseModel;

import java.util.Objects;

@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_name_role", columnList = "name"),
        @Index(name = "idx_active_role", columnList = "active")
})
public class RoleModel extends BaseModel {

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    private Boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleModel roleModel = (RoleModel) o;
        return Objects.equals(name, roleModel.name) && Objects.equals(active, roleModel.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, active);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

