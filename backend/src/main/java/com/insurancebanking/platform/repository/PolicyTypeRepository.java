package com.insurancebanking.platform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.PolicyType;

public interface PolicyTypeRepository extends JpaRepository<PolicyType, UUID> { }