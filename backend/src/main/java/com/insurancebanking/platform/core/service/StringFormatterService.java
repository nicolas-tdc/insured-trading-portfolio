package com.insurancebanking.platform.core.service;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface StringFormatterService {
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        // Keep first 2 characters of local part
        String visibleLocal = local.length() <= 2 ? local : local.substring(0, 2);
        String maskedLocal = visibleLocal + "*".repeat(Math.max(0, local.length() - 2));

        // Mask domain parts (preserve dots)
        String[] domainParts = domain.split("\\.");
        String maskedDomain = Arrays.stream(domainParts)
                .map(part -> "*".repeat(part.length()))
                .collect(Collectors.joining("."));

        return maskedLocal + "@" + maskedDomain;
    }
}