package com.insurancebanking.platform.auth.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.core.model.BaseEntity;
import com.insurancebanking.platform.policy.model.Policy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity representing a system user.
 * 
 * Extends BaseEntity to inherit common fields like id.
 * Stores user credentials, personal info, status, and associations to roles, accounts, and policies.
 */
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    /**
     * Convenience constructor to create a new user with essential fields.
     *
     * @param email     the user's unique email address
     * @param password  the user's encoded password
     * @param firstName user's first name
     * @param lastName  user's last name
     */
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Unique email address identifying the user.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * User's password, stored encoded.
     * Ignored during JSON serialization for security.
     */
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    /**
     * User's first name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * User's last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * User account status, e.g., "active".
     * Defaults to "active" if not set explicitly.
     */
    @Column(name = "status")
    @Builder.Default
    private String status = "active";

    /**
     * Roles assigned to the user.
     * Many-to-many relationship via the user_roles join table.
     * Fetch type is LAZY with Hibernate fetch mode SELECT.
     * Managed reference for JSON serialization.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Role> roles = new LinkedHashSet<>();

    /**
     * Accounts owned by the user.
     * One-to-many relationship mapped by "user" field in Account entity.
     * Fetch type is LAZY with Hibernate fetch mode SELECT.
     * Managed reference for JSON serialization.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private Set<Account> accounts = new LinkedHashSet<>();

    /**
     * Policies associated with the user.
     * One-to-many relationship mapped by "user" field in Policy entity.
     * Fetch type is LAZY with Hibernate fetch mode SELECT.
     * Managed reference for JSON serialization.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private Set<Policy> policies = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", status=" + status +
                "}";
    }
}
