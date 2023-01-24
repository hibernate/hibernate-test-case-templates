package org.hibernate.bugs.entities.common;


import static org.hibernate.bugs.entities.common.BooleanUtil.not;
import static org.hibernate.bugs.entities.common.Validator.validateNotNull;

import java.util.UUID;

public class IdGenerator {

    public static final int    UUID_LENGTH_WITHOUT_SEPARATOR = 32;
    public static final String SEPARATOR                     = "-";

    private IdGenerator() {
    }

    public static String generate(IdPrefix idPrefix) {
        return idPrefix.getPrefix() + "-" + UUID.randomUUID().toString().replace(SEPARATOR, "");
    }

    public static void validateId(IdPrefix idPrefix, String value) {
        validateNotNull(value);
        if (not(isStartingWithPrefix(idPrefix, value))) {
            throw new IllegalStateException("id '" + value + "' must always start with the prefix '" + idPrefix.getPrefix() + "'");
        }
        if (not(isLengthCorrect(idPrefix, value))) {
            throw new IllegalStateException("id '" + value + "' must be of length '" + lengthOfPrefixWithUuid(idPrefix) + "'");
        }
    }

    public static boolean isValidId(IdPrefix idPrefix, String value) {
        return isLengthCorrect(idPrefix, value) && isStartingWithPrefix(idPrefix, value);
    }

    private static boolean isLengthCorrect(IdPrefix idPrefix, String value) {
        return value.length() == lengthOfPrefixWithUuid(idPrefix);
    }

    private static boolean isStartingWithPrefix(IdPrefix idPrefix, String value) {
        return value.startsWith(idPrefix.getPrefix());
    }

    private static int lengthOfPrefixWithUuid(IdPrefix idPrefix) {
        return idPrefix.getPrefix().length() + SEPARATOR.length() + UUID_LENGTH_WITHOUT_SEPARATOR;
    }

}
