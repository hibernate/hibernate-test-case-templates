package org.hibernate;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@NamedQueries(value = @NamedQuery(name = "getEntityA",
query = "SELECT COUNT(adminPermission) FROM org.hibernate.IEntityA adminPermission" +
        " LEFT OUTER JOIN adminPermission.bs entityBs" +
        " LEFT OUTER JOIN entityBs.cs entityCs" +
        " WHERE (entityCs = :adminUser)"
))
@Entity
public class EntityA implements IEntityA {

    @Id
    private long id;
    private String name;

    @OneToMany(mappedBy = "a", targetEntity = EntityB.class)
    private Set<IEntityB> bs;

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
    public Set<IEntityB> getBs() {
        return bs;
    }

    @Override
    public void setBs(Set<IEntityB> bs) {
        this.bs = bs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityA entityA = (EntityA) o;
        return id == entityA.id && Objects.equals(name, entityA.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
