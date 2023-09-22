package org.hibernate.bugs.model;

import java.io.Serializable;

// Using a record for the IdClass
// will save us lots of boilerplate code
// (getters, setters, equals, hashCode, ...)
public record BaseModelId(
    String accountName,
    String accountType
) implements Serializable {
}
