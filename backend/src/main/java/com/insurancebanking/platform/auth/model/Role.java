package com.insurancebanking.platform.auth.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insurancebanking.platform.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a security role in the system.
 * 
 * Each role has a unique name and can be associated with multiple users.
 * The class extends BaseEntity to inherit common entity fields like id.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    /**
     * Unique name of the role (e.g., ROLE_ADMIN, ROLE_CUSTOMER).
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * Users associated with this role.
     * This is the inverse side of the many-to-many relationship mapped by 'roles' field in User entity.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                "}";
    }

    /**
     * Equality based on non-null entity id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return this.id != null && this.id.equals(role.id);
    }

    /**
     * Hash code based on entity id if present.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
