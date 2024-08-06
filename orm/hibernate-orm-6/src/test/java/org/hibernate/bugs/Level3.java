package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Level3 {

    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Level2 parent;

    public Level3() {
    }

    public Level3(Level2 parent, Long id) {
        this.parent = parent;
        this.id = id;
        parent.getChilds().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level2 getParent() {
        return parent;
    }

    public void setParent(Level2 parent) {
        this.parent = parent;
    }
}
