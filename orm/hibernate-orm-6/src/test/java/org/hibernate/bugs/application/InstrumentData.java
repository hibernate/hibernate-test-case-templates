package org.hibernate.bugs.application;

import java.util.List;

public record InstrumentData(
        String code,
        String category,
        String description,
        List<InstrumentLine> lineKeys) {

    record InstrumentLine(
            String id,
            String countryCode,
            String description) {

    }
}
