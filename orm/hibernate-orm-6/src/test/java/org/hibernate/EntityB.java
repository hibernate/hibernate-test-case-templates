package org.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.Set;

@Entity
public class EntityB implements IEntityB {

    @Id
    private long id;
    private String something;

    @OneToMany(mappedBy = "b", targetEntity = EntityC.class)
    private Set<IEntityC> cs;

    @Override
    public IEntityA getA() {
        return a;
    }

    @Override
    public void setA(IEntityA a) {
        this.a = a;
    }

    @ManyToOne(targetEntity = EntityA.class)
    private IEntityA a;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getSomething() {
        return something;
    }

    @Override
    public void setSomething(String something) {
        this.something = something;
    }

    @Override
    public Set<IEntityC> getCs() {
        return cs;
    }

    @Override
    public void setCs(Set<IEntityC> cs) {
        this.cs = cs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityB entityB = (EntityB) o;
        return id == entityB.id && Objects.equals(something, entityB.something);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, something);
    }
}
