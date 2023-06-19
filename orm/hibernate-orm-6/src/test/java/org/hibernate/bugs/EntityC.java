package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityC {
    @Id
    private Long id;

    private Character cName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getcName() {
        return cName;
    }

    public void setcName(Character cName) {
        this.cName = cName;
    }
}
