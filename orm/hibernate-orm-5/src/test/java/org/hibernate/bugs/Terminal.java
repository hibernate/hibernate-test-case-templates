package org.hibernate.bugs;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Terminal implements Serializable {
    @EmbeddedId
    private TerminalId id;

    public Terminal() {
        id = new TerminalId();
    }

    public TerminalId getId() {
        return id;
    }
}
