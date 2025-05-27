package com.insurancebanking.platform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> { }