package org.hibernate.bugs;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@MappedSuperclass
public class TerminalId implements Serializable {
    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TerminalId studentId = (TerminalId) o;
        return Objects.equals(getId(), studentId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
