package com.insurancebanking.platform.auth.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.insurancebanking.platform.auth.dto.JwtResponse;
import com.insurancebanking.platform.auth.dto.LoginRequest;
import com.insurancebanking.platform.auth.dto.SignupRequest;
import com.insurancebanking.platform.auth.dto.UserResponse;
import com.insurancebanking.platform.auth.exception.EmailAlreadyInUseException;
import com.insurancebanking.platform.auth.model.Role;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.auth.security.JwtUtils;
import com.insurancebanking.platform.auth.service.RoleService;
import com.insurancebanking.platform.auth.service.UserService;
import com.insurancebanking.platform.core.dto.MessageResponse;

/**
 * Controller responsible for authentication and registration endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            RoleService roleService,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param loginRequest the login credentials
     * @return the JWT response with token and user details
     */
    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserResponse userResponse = UserResponse.from(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt, userResponse));
    }

    /**
     * Registers a new user.
     *
     * @param signupRequest the registration information
     * @return a confirmation message
     */
    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByEmail(signupRequest.email())) {
            throw new EmailAlreadyInUseException(signupRequest.email());
        }

        // Create and save new user
        User user = new User(
                signupRequest.email(),
                passwordEncoder.encode(signupRequest.password()),
                signupRequest.firstName(),
                signupRequest.lastName()
        );

        Role customerRole = roleService.findByName("ROLE_CUSTOMER");
        user.setRoles(Collections.singleton(customerRole));

        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
