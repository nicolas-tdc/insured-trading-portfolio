package com.insurancebanking.platform.auth;

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
import com.insurancebanking.platform.auth.repository.RoleRepository;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.auth.security.JwtUtils;
import com.insurancebanking.platform.auth.security.UserDetailsImpl;
import com.insurancebanking.platform.core.dto.MessageResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value="/signin", produces="application/json")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        String errorMessage = "Error signing in";
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            UserResponse userResponse = UserResponse.from(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt, userResponse));

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
        }

    }

    @PostMapping(value="/signup", produces="application/json")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        String errorMessage = "Error signing up";
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage + ": Email is already taken"));
            }

            // Create new user's account
            User user = new User(signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getFirstName(),
                    signUpRequest.getLastName());

            Set<Role> roles = new HashSet<>();
            roleRepository.findByName("ROLE_CUSTOMER").ifPresent(roles::add);

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
        }
    }
}