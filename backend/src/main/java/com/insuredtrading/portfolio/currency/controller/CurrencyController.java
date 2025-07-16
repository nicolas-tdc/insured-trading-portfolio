package com.insuredtrading.portfolio.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insuredtrading.portfolio.core.dto.MessageResponse;
import com.insuredtrading.portfolio.currency.service.CurrencyService;

/**
 * REST controller for handling currency-related API endpoints.
 *
 * All endpoints require authenticated users.
 */
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CurrencyController.class);

    /**
     * Retrieves the list of available currencies.
     *
     * @return HTTP 200 with the currency list if successful,
     *         or HTTP 400 with error message if an exception occurs.
     */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> getCurrencies() {
        try {
            // Return the list of currencies from the service
            return ResponseEntity.ok(currencyService.getList());

        } catch (Exception e) {
            // Log error and return bad request with error message
            String errorMessage = "Error getting currencies";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}
