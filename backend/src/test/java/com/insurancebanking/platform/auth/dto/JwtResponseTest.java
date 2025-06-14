package com.insurancebanking.platform.auth.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class JwtResponseTest {

    @Test
    void constructor_withExplicitType_shouldWork() {
        UserResponse user = new UserResponse(UUID.randomUUID(), "test@example.com", List.of("ROLE_USER"));
        JwtResponse response = new JwtResponse("token123", "Bearer", user);

        assertThat(response.token()).isEqualTo("token123");
        assertThat(response.type()).isEqualTo("Bearer");
        assertThat(response.user()).isEqualTo(user);
    }

    @Test
    void constructor_withDefaultType_shouldSetBearer() {
        UserResponse user = new UserResponse(UUID.randomUUID(), "test@example.com", List.of("ROLE_USER"));
        JwtResponse response = new JwtResponse("token456", user);

        assertThat(response.token()).isEqualTo("token456");
        assertThat(response.type()).isEqualTo("Bearer");
        assertThat(response.user()).isEqualTo(user);
    }
}
