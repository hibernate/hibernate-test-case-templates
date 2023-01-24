package org.hibernate.bugs.entities.common;

public enum IdPrefix {
    ADDRESS("AD"),
    OUTBOX_MESSAGE("OBM"),
    USER("U");

    private final String prefix;

    IdPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
