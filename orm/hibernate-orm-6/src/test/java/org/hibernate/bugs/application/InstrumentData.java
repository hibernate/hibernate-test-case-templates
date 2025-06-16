package org.hibernate.bugs.application;

import java.util.List;

public record InstrumentData(
        String code,
        String category,
        String description,
        List<LineKeyData> lineKeys) {

    public record LineKeyData(
            String id,
            String currencyCode,
            String description) {

    }
}
