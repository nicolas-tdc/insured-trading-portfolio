package com.insuredtrading.portfolio.core.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseEntity
 *
 * Abstract base class for JPA entities providing common fields:
 * - id: UUID primary key, generated automatically
 * - createdAt: timestamp when the entity was created
 * - updatedAt: timestamp when the entity was last updated
 *
 * Uses Hibernate UUID generator and Spring Data auditing annotations for timestamps.
 */
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primary key identifier of type UUID, generated automatically and immutable.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @Setter
    @Getter
    protected UUID id;

    /**
     * Timestamp of entity creation, set once on creation and never updated.
     * Defaults to current instant.
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * Timestamp of last entity update, updated automatically on modification.
     * Defaults to current instant.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();
}
