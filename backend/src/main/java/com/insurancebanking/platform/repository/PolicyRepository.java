package com.insurancebanking.platform.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {
    List<Policy> findByUser_Id(UUID userId);
}