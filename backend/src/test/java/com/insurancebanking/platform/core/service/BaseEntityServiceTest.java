package com.insurancebanking.platform.core.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BaseEntityServiceTest {

    private final BaseEntityService service = new BaseEntityService();

    @Test
    void generateEntityPublicIdentifier_shouldIncludePrefixAnd9DigitNumber() {
        String prefix = "TRF";
        String identifier = service.generateEntityPublicIdentifier(prefix);

        assertThat(identifier).startsWith(prefix);
        assertThat(identifier.substring(prefix.length())).matches("\\d{9}");
    }

    @Test
    void generateEntityPublicIdentifier_shouldReturnDifferentValues() {
        String id1 = service.generateEntityPublicIdentifier("ACC");
        String id2 = service.generateEntityPublicIdentifier("ACC");

        assertThat(id1).isNotEqualTo(id2);
    }
}
