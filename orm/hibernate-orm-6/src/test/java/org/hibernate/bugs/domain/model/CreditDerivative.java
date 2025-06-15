package org.hibernate.bugs.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CreditDerivative(
        @Column(name = "CURRENCY_CODE", table = "INSTRUMENT_CREDIT_DERIVATIVE")
        String currencyCode, // Cannot be an Embedded as first subcomponent, needs to be declared after (HHH-19542)
        @Column(name = "INDEX_SUB_FAMILY", table = "INSTRUMENT_CREDIT_DERIVATIVE")
        String indexSubFamily) {
}
