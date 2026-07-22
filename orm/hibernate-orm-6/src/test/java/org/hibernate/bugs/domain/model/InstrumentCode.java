package org.hibernate.bugs.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record InstrumentCode(
        @Column(name = "INSTRUMENT_CODE")
        String id) implements Identity, Serializable {
}
