package com.insurancebanking.platform.auth.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.auth.dto.JwtResponse;
import com.insurancebanking.platform.auth.dto.LoginRequest;
import com.insurancebanking.platform.auth.dto.SignupRequest;
import com.insurancebanking.platform.auth.dto.UserResponse;
import com.insurancebanking.platform.auth.model.Role;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.auth.security.JwtUtils;
import com.insurancebanking.platform.auth.service.RoleService;
import com.insurancebanking.platform.auth.service.UserService;
import com.insurancebanking.platform.core.dto.MessageResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value="/signin", produces="application/json")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserResponse userResponse = UserResponse.from(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt, userResponse));

        } catch (Exception e) {
            String errorMessage = "Error signing in";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
        }

    }

    @PostMapping(value="/signup", produces="application/json")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            if (userService.existsByEmail(signUpRequest.getEmail())) {
                String errorMessage = "Error signing up: Email is already taken";
                logger.error(errorMessage);

                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
            }

            // Create new user's account
            User user = new User(signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getFirstName(),
                    signUpRequest.getLastName());

            Role customerRole = roleService.findByName("ROLE_CUSTOMER");
            if (customerRole == null) {        
                String errorMessage = "Error: role customer not found";
                logger.error(errorMessage);

                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
            }

            Set<Role> roles = new HashSet<>();
            roles.add(customerRole);
            user.setRoles(roles);

            userService.saveUser(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully"));

        } catch (Exception e) {
            String errorMessage = "Error signing up";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
        }
    }
}