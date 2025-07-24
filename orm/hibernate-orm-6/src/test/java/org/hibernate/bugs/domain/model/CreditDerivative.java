package org.hibernate.bugs.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CreditDerivative(
        @Column(name = "INDEX_SUB_FAMILY", table = "INSTRUMENT_CREDIT_DERIVATIVE")
        String indexSubFamily,
        CurrencyCode currencyCode) {
}
