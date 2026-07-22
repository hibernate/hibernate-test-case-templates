package org.hibernate.bugs.b;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import org.hibernate.bugs.A;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class B extends A{
    @Id
    protected long id;
    protected String bname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
