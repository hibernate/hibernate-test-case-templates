/*
 * Copyright Red Hat, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Red Hat trademarks are not licensed under GPLv3. No permission is
 * granted to use or replicate Red Hat trademarks that are incorporated
 * in this software or its documentation.
 */
package org.hibernate.bugs;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

/**
 * System purpose usage
 *
 * <p>Usage represents the class of usage for a given system or subscription.
 */
public enum Usage {
  EMPTY(""),
  PRODUCTION("Production");

  private final String value;

  Usage(String value) {
    this.value = value;
  }

  public static Usage fromString(String value) {
    if (value == null || value.isEmpty()) {
      return EMPTY;
    } else if ("production".equalsIgnoreCase(value)) {
      return PRODUCTION;
    }
    return PRODUCTION;
//    throw new IllegalArgumentException();
  }

  public String getValue() {
    return value;
  }

  /** JPA converter for Usage */
  @Converter(autoApply = true)
  public static class EnumConverter implements AttributeConverter<Usage, String> {

    @Override
    public String convertToDatabaseColumn(Usage attribute) {
      if (attribute == null) {
        return null;
      }
      return attribute.getValue();
    }

    @Override
    public Usage convertToEntityAttribute(String dbData) {
      return Objects.nonNull(dbData) ? Usage.fromString(dbData) : null;
    }
  }
}
