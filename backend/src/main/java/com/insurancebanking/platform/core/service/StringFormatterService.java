package com.insurancebanking.platform.core.service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * StringFormatterService
 *
 * Provides string formatting utilities such as masking sensitive parts of strings.
 */
public interface StringFormatterService {

    /**
     * Masks an email address by partially hiding the local part and fully masking the domain parts.
     * 
     * The first two characters of the local part are visible; the rest are replaced by '*'.
     * Each segment of the domain (split by '.') is fully masked with '*'.
     *
     * @param email the email address to mask
     * @return masked email address or original input if invalid
     */
    public static String maskEmail(String email) {
        // Return original if email is null or does not contain '@'
        if (email == null || !email.contains("@")) {
            return email;
        }

        // Split email into local and domain parts
        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        // Show first 2 characters of local part or entire local if shorter
        String visibleLocal = local.length() <= 2 ? local : local.substring(0, 2);
        // Mask remaining local part characters with '*'
        String maskedLocal = visibleLocal + "*".repeat(Math.max(0, local.length() - 2));

        // Mask each domain segment fully with '*', preserving dots
        String[] domainParts = domain.split("\\.");
        String maskedDomain = Arrays.stream(domainParts)
                .map(part -> "*".repeat(part.length()))
                .collect(Collectors.joining("."));

        // Reconstruct masked email and return
        return maskedLocal + "@" + maskedDomain;
    }
}
