package com.insuredtrading.portfolio.auth.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.insuredtrading.portfolio.auth.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter executed once per request to validate and parse JWT token from the Authorization header.
 * If the token is valid, it sets the Spring Security context with the authenticated user details.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Filters incoming HTTP requests to:
     * - Extract the JWT token from the Authorization header
     * - Validate the token
     * - Retrieve user details from the token
     * - Set authentication in the SecurityContext
     *
     * @param request     incoming HTTP request
     * @param response    HTTP response
     * @param filterChain filter chain to continue processing
     * @throws ServletException in case of servlet errors
     * @throws IOException      in case of I/O errors
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Parse JWT token from Authorization header
            String jwt = parseJwt(request);
            // Validate the token and process if valid
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Extract username/email from JWT token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // Load user details from database/service
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Extract roles and convert to GrantedAuthority list
                List<GrantedAuthority> authorities = jwtUtils.getUserRolesFromJwtToken(jwt)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
                // Create authentication token for Spring Security context
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                authorities
                        );
                // Set additional details from request
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.JwtException e) {
            // Log JWT processing errors
            logger.error("JWT error during authentication: {}", e);
        } catch (RuntimeException e) {
            // Log unexpected runtime exceptions during authentication
            logger.error("Unexpected error during authentication: {}", e);
        }
        // Continue filter chain execution
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * @param request the HTTP request
     * @return the JWT token if present and properly formatted, null otherwise
     */
    private String parseJwt(HttpServletRequest request) {
        // Get Authorization header value
        String headerAuth = request.getHeader("Authorization");
        // Check if header has text and starts with Bearer prefix
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Return JWT token substring after "Bearer "
            return headerAuth.substring(7);
        }
        // Return null if no valid token found
        return null;
    }
}
