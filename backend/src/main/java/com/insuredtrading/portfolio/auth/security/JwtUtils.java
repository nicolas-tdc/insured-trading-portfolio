package com.insuredtrading.portfolio.auth.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.insuredtrading.portfolio.auth.model.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * Utility class for generating, parsing, and validating JSON Web Tokens (JWT).
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;  // Secret key used for signing tokens (Base64 encoded)

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;  // Token expiration time in milliseconds

    /**
     * Decodes the Base64 encoded secret and returns the signing key.
     *
     * @return Key used for signing JWT tokens
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Generates a JWT token containing the username and roles from the authentication object.
     * Sets issued and expiration dates and signs the token.
     *
     * @param authentication the authenticated user's authentication object
     * @return the generated JWT token string
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Build JWT token with subject, roles, issued date, expiration, and signature
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .claim("roles", userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS512)
            .compact();
    }

    /**
     * Parses the JWT token to extract the username (subject).
     *
     * @param token the JWT token string
     * @return username contained in the token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extracts the list of role names from the "roles" claim in the JWT token.
     * Handles exceptions and logs any issues.
     *
     * @param token the JWT token string
     * @return list of role names or empty list if roles claim is missing or invalid
     */
    public List<String> getUserRolesFromJwtToken(String token) {
        try {
            Object rolesClaim = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("roles");

            if (rolesClaim instanceof List<?> list) {
                // Convert roles claim to list of strings
                return list.stream()
                        .map(Object::toString)
                        .toList();
            } else {
                logger.warn("Roles claim was not a list");
                return List.of();
            }
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            logger.error("Error extracting roles from token", e);
            return List.of();
        }
    }

    /**
     * Validates the JWT token signature and structure.
     * Logs specific errors for different token validation exceptions.
     *
     * @param authToken JWT token string to validate
     * @return true if token is valid; false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Parse token to validate signature and claims
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
