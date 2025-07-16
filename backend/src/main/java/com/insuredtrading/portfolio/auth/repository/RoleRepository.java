package com.insuredtrading.portfolio.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insuredtrading.portfolio.auth.model.Role;

/**
 * Repository interface for Role entities.
 * Provides CRUD operations and a method to find a Role by its name.
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Finds a role by its unique name.
     *
     * @param name the name of the role to find
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByName(String name);
}
