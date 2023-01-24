package org.hibernate.bugs.entities.common;

import static java.util.Objects.isNull;

import jakarta.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class SingleStringValueHolder {

    private final String value;

    protected SingleStringValueHolder() {
        this.value = null;
    }

    protected SingleStringValueHolder(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (SingleStringValueHolder) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public String toString() {
        if (isNull(this.value)) {
            return "SingleStringValueHolder-null";
        }
        return "SingleStringValueHolder-" + this.value;
    }

}
