package org.hibernate.bugs.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CurrencyCode(
        @Column(name = "CURRENCY_CODE")
        String code) {

    public CurrencyCode {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Currency code must be provided.");
        if (code.trim().length() != 3) throw new IllegalArgumentException("Currency code must have 3 characters.");

        code = code.trim().toUpperCase().intern();
    }
}
