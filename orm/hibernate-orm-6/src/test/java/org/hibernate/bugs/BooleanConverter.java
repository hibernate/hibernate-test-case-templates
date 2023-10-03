package org.hibernate.bugs;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Long> {

    private static final Long LONG_TRUE = 1L;
    private static final Long LONG_FALSE = null;

    @Override
    public Long convertToDatabaseColumn(Boolean attribute) {
        return attribute ? 1L : null;
    }

    @Override
    public Boolean convertToEntityAttribute(Long dbData) {
        return 1L == dbData ? Boolean.TRUE : Boolean.FALSE;
    }
}
