package com.insuredtrading.portfolio.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insuredtrading.portfolio.auth.model.User;

/**
 * Repository interface for User entities.
 * Provides CRUD operations and methods to find users by email and check email existence.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their unique email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user exists with the given email.
     *
     * @param email the email address to check
     * @return true if a user with the email exists, false otherwise
     */
    Boolean existsByEmail(String email);
}
