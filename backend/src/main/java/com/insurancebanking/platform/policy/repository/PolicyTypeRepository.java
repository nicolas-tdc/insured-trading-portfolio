package com.insurancebanking.platform.policy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.policy.model.PolicyType;

public interface PolicyTypeRepository extends JpaRepository<PolicyType, UUID> { }