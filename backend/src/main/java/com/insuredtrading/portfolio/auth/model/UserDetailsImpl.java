package com.insuredtrading.portfolio.auth.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Implementation of Spring Security's UserDetails interface
 * that represents authenticated user details including
 * authorities granted based on user roles.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier of the user.
     */
    private final UUID id;

    /**
     * Email of the user used as username for authentication.
     */
    private final String email;

    /**
     * Password of the user, ignored in JSON serialization for security.
     */
    @JsonIgnore
    private final String password;

    /**
     * Authorities granted to the user derived from roles.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor for UserDetailsImpl.
     * 
     * @param id          unique user ID
     * @param email       user email (username)
     * @param password    encoded user password
     * @param authorities granted authorities from roles
     */
    public UserDetailsImpl(UUID id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Static factory method to build UserDetailsImpl from User entity.
     * Maps user roles to GrantedAuthority list.
     * 
     * @param user the User entity to convert
     * @return UserDetailsImpl with data from user
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return the UUID of the user
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the email of the user (username)
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used for authentication, which is email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user account has expired.
     * Always returns true since expiration is not implemented.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user account is locked.
     * Always returns true since locking is not implemented.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user credentials have expired.
     * Always returns true since credential expiration is not implemented.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * Always returns true since enabling/disabling is not implemented here.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
