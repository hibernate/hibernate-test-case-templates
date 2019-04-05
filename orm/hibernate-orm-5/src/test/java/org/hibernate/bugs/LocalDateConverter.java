package org.hibernate.bugs;

import java.sql.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    private static final Logger logger = LoggerFactory.getLogger(LocalDateConverter.class);

    public LocalDateConverter() {
        logger.info("Initializing new LocalDateConverter");
    }

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return attribute == null ? null : new Date(attribute.toDate().getTime());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : LocalDate.fromDateFields(dbData);
    }
}
