package com.insurancebanking.platform.currency.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.currency.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> { }