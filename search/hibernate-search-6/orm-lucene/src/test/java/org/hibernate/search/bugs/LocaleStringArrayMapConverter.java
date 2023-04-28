/*
 * TheVegCat - The Vegan Catalog.
 * Copyright (C) H.Lo
 * mailto:horvoje@gmail.com
 */
package org.hibernate.search.bugs;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LocaleStringArrayMapConverter implements AttributeConverter<Map<Locale, String[]>, String>, Serializable {

    @Override
    public String convertToDatabaseColumn(final Map<Locale, String[]> attribute) {
    	if (attribute == null) {
			return null;
		}
    	try {
            final String toString = new ObjectMapper().writeValueAsString(attribute);
            return toString;
        }
        catch (final JsonProcessingException e) {
	        return null;
        }
    }

    @Override
    public Map<Locale, String[]> convertToEntityAttribute(final String dbData) {
    	if (dbData == null) {
			return null;
		}
    	try {
            return new ObjectMapper().readValue(dbData, typeReference());
        }
        catch (final JsonProcessingException e) {
	        return null;
        }
    }

    private static final TypeReference<Map<Locale, String[]>> typeReference() {
        return new TypeReference<>() {
            //
        };
    }

}
