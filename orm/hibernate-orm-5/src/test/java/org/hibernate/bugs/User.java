package org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @EmbeddedId
    private UserId id;

    @Column(name = "roll_number")
    private int rollNumber;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Terminal terminal;

    public User() {
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
