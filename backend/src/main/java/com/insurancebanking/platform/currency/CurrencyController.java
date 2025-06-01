package com.insurancebanking.platform.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.auth.AuthController;
import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.currency.repository.CurrencyRepository;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    @GetMapping(value="", produces="application/json")
    public ResponseEntity<?> getCurrencies() {

        String errorMessage = "Error getting currencies";
        try {

            return ResponseEntity.ok(currencyRepository.findAll());

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}
