package com.insuredtrading.portfolio.auth.controller;

import java.net.URI;
import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.insuredtrading.portfolio.account.model.Account;
import com.insuredtrading.portfolio.account.model.AccountStatus;
import com.insuredtrading.portfolio.account.model.AccountType;
import com.insuredtrading.portfolio.account.service.AccountService;
import com.insuredtrading.portfolio.auth.dto.JwtResponse;
import com.insuredtrading.portfolio.auth.dto.LoginRequest;
import com.insuredtrading.portfolio.auth.dto.SignupRequest;
import com.insuredtrading.portfolio.auth.dto.UserResponse;
import com.insuredtrading.portfolio.auth.exception.BadCredentialsException;
import com.insuredtrading.portfolio.auth.exception.EmailAlreadyInUseException;
import com.insuredtrading.portfolio.auth.model.Role;
import com.insuredtrading.portfolio.auth.model.User;
import com.insuredtrading.portfolio.auth.model.UserDetailsImpl;
import com.insuredtrading.portfolio.auth.security.JwtUtils;
import com.insuredtrading.portfolio.auth.service.RoleService;
import com.insuredtrading.portfolio.auth.service.UserService;
import com.insuredtrading.portfolio.core.dto.MessageResponse;

/**
 * Controller responsible for authentication and registration endpoints.
 * Provides RESTful API for signing in, signing up and fetching current user info.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            RoleService roleService,
            AccountService accountService,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param loginRequest the login request containing email and password
     * @return ResponseEntity containing a JWT token and user details on successful authentication
     */
    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserResponse userResponse = UserResponse.from(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt, userResponse));

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    /**
     * Registers a new user and creates a default checking account with initial balance.
     *
     * @param signupRequest the signup request containing user registration data
     * @return ResponseEntity with a success message and HTTP 201 Created status with Location header pointing to new user resource
     * @throws EmailAlreadyInUseException if the email is already registered
     */
    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByEmail(signupRequest.email())) {
            throw new EmailAlreadyInUseException(signupRequest.email());
        }

        // Create new user with encoded password
        User user = new User(
                signupRequest.email(),
                passwordEncoder.encode(signupRequest.password()),
                signupRequest.firstName(),
                signupRequest.lastName());

        // Assign default customer role
        Role customerRole = roleService.findByName("ROLE_CUSTOMER");
        user.setRoles(Set.of(customerRole));
        userService.saveUser(user);

        // Create default checking account with starting balance
        Account account = Account.builder()
                .user(user)
                .accountType(AccountType.CHECKING)
                .currencyCode("EUR")
                .accountNumber(accountService.generateAccountNumber())
                .accountStatus(AccountStatus.ACTIVE)
                .balance(BigDecimal.valueOf(10000))
                .build();

        accountService.save(account);

        URI location = URI.create("/api/users/" + user.getId());
        return ResponseEntity.created(location).body(new MessageResponse(user.getEmail() + " registered successfully"));
    }

    /**
     * Retrieves the currently authenticated user's information.
     *
     * @param userDetails the authenticated user's details injected by Spring Security
     * @return ResponseEntity containing the current user's information
     */
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse userResponse = UserResponse.from(userDetails);
        return ResponseEntity.ok(userResponse);
    }
}
