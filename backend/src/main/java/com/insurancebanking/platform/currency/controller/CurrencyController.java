package com.insurancebanking.platform.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.currency.service.CurrencyService;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping(value="", produces="application/json")
    public ResponseEntity<?> getCurrencies() {

        String errorMessage = "Error getting currencies";
        try {

            return ResponseEntity.ok(currencyService.getCurrencies());

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}
