package com.insurancebanking.platform.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable and configure Cross-Origin Resource Sharing (CORS).
 * Allows requests from the frontend application to the backend API.
 */
@Configuration
public class CorsConfig {

    @Value("${frontend.url}")
    private String frontendUrl;  // URL of the frontend allowed for CORS requests

    /**
     * Defines a WebMvcConfigurer bean to customize CORS settings.
     *
     * @return WebMvcConfigurer with CORS mapping configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configures CORS mappings for API endpoints.
             *
             * @param registry CorsRegistry to add mappings
             */
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                // Allow CORS requests to /api/** endpoints
                registry.addMapping("/api/**")
                        // Restrict allowed origins to frontendUrl
                        .allowedOrigins(frontendUrl)
                        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
                        .allowedMethods("*")
                        // Allow all headers in the request
                        .allowedHeaders("*")
                        // Expose Authorization header in response
                        .exposedHeaders("Authorization");
            }
        };
    }
}
