package org.hibernate.bugs;

import java.sql.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<String, Date> {

    @Override
    public Date convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : new Date(Long.valueOf(attribute));
    }

    @Override
    public String convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : Long.toString(dbData.getTime());
    }
}
