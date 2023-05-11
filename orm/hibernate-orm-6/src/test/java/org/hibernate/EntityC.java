package org.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class EntityC implements IEntityC {

    @Id
    private long id;
    private String name;

    @ManyToOne(targetEntity = EntityB.class)
    private IEntityB b;

    @Override
    public IEntityB getB() {
        return b;
    }

    @Override
    public void setB(IEntityB b) {
        this.b = b;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityC entityC = (EntityC) o;
        return id == entityC.id && Objects.equals(name, entityC.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
