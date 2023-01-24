package org.hibernate.bugs.entities.common;

public class Validator {

    public static final String LENGTH_OF                  = "length of '";
    public static final String IS_LONGER_THAN_THE_ALLOWED = "' is longer than the allowed '";
    public static final String INPUT_IS_NULL              = "input is null";
    public static final String INPUT_IS_EMPTY             = "input is empty";

    private Validator() {
    }

    public static void validateNotNull(Object input) {
        if (input == null) {
            throw new IllegalStateException(INPUT_IS_NULL);
        }
    }

    public static void validateNotNull(Object input, String details) {
        if (input == null) {
            throw new IllegalStateException(INPUT_IS_NULL + " for details '" + details + "'");
        }
    }

    public static void validateNotNullAndNotEmptyString(SingleStringValueHolder input) {
        if (input == null) {
            throw new IllegalStateException(INPUT_IS_NULL);
        }
        if (input.getValue() == null) {
            throw new IllegalStateException(INPUT_IS_NULL);
        }
        if ("".equals(input.getValue().trim())) {
            throw new IllegalStateException(INPUT_IS_EMPTY);
        }
    }

    public static void validateMaxLength(String input, int maxLength) {
        if (input == null) {
            return;
        }
        if (input.length() > maxLength) {
            throw new IllegalStateException(LENGTH_OF + input + IS_LONGER_THAN_THE_ALLOWED + maxLength + "' for input '" + input + "'");
        }
    }

    public static void validateTrimmedMaxLength(String input, int maxLength) {
        if (input == null) {
            return;
        }
        if (input.trim().length() > maxLength) {
            throw new IllegalStateException(LENGTH_OF + input.trim() + IS_LONGER_THAN_THE_ALLOWED + maxLength + "' for input '" + input + "'");
        }
    }

    public static void validateTrimmedMinAndMaxLength(String input, int minLength, int maxLength) {
        validateNotNull(input);
        if (input.trim().length() < minLength) {
            throw new IllegalStateException(LENGTH_OF + input.trim() + "' is shorter than the allowed '" + minLength + "' for input '" + input + "'");
        }
        validateTrimmedMaxLength(input, maxLength);
    }

}
