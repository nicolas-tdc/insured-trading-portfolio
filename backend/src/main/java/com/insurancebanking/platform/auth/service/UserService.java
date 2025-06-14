package com.insurancebanking.platform.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;

/**
 * Service for user management and persistence.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Saves a new user to the repository.
     *
     * @param user the user to save
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
