package org.hibernate.bugs.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record InstrumentLineKey(
        @Column(name = "LINE_KEY")
        String id) implements Identity, Serializable {
}
