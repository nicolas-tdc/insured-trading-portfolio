package com.insurancebanking.platform.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUser_IdOrderByCreatedAtDesc(UUID userId);
}