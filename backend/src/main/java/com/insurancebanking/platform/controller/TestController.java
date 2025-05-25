package com.insurancebanking.platform.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/me")
    public Object currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }

    @GetMapping("/user")
    public String authenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return "Authenticated user: " + auth.getName();
        }
        return "No authenticated user";
    }
}