package com.insurancebanking.platform.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.auth.repository.UserRepository;

/**
 * Spring Security UserDetailsService implementation.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their email (used as username).
     *
     * @param email the user's email
     * @return user details for authentication
     * @throws UsernameNotFoundException if no user found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }
}
