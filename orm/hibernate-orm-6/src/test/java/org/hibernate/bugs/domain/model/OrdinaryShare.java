package org.hibernate.bugs.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("ORD")
public class OrdinaryShare extends Instrument {

    public OrdinaryShare(
            final InstrumentCode instrumentCode,
            final String category,
            final String description,
            final List<InstrumentLine> instrumentLines) {
        super(instrumentCode, category, description, instrumentLines);
    }

    protected OrdinaryShare() {
        super();
    }
}
