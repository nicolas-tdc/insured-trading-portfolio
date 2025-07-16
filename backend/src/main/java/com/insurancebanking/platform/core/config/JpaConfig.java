package com.insurancebanking.platform.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA Auditing.
 * This allows automatic population of auditing fields such as createdDate and lastModifiedDate
 * on JPA entities annotated with auditing annotations.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
