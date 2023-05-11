package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

import java.util.Objects;


@FilterDefs(value = @FilterDef(name = "filterA", defaultCondition = "name = :name", parameters = @ParamDef(name = "name", type = String.class)))
@Filter(name = "filterA")
@Entity
public class EntityA {
    @Id
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
