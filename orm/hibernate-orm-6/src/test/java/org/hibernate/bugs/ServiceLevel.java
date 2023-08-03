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
 * System purpose service level
 *
 * <p>SLA or service level agreement is defined on a given subscription, and can be set as an
 * attribute on a system to associate it with a specific SLA requirement.
 */
public enum ServiceLevel {
  EMPTY(""),
  PREMIUM("Premium");

  private final String value;

  ServiceLevel(String value) {
    this.value = value;
  }

  public static ServiceLevel fromString(String value) {
    if (value == null || value.isEmpty()) {
      return EMPTY;
    } else if ("premium".equalsIgnoreCase(value)) {
      return PREMIUM;
    }
    return PREMIUM;
//    throw new IllegalArgumentException();
  }

  public String getValue() {
    return value;
  }

  /** JPA converter for ServiceLevel */
  @Converter(autoApply = true)
  public static class EnumConverter implements AttributeConverter<ServiceLevel, String> {

    @Override
    public String convertToDatabaseColumn(ServiceLevel attribute) {
      return Objects.nonNull(attribute) ? attribute.getValue() : null;
    }

    @Override
    public ServiceLevel convertToEntityAttribute(String dbData) {
      return Objects.nonNull(dbData) ? ServiceLevel.fromString(dbData) : null;
    }
  }
}
