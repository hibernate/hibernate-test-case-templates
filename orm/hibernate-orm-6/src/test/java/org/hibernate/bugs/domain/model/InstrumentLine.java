package org.hibernate.bugs.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record InstrumentLine(
        InstrumentLineKey key,
        CurrencyCode currencyCode,
        @Column(name = "DESCRIPTION")
        String description) {
}
