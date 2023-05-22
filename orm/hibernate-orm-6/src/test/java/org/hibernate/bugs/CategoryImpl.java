package org.hibernate.bugs;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CategoryImpl implements Category {


    @Id
    @Column(name = "CATEGORY_ID")
    protected Long id;

    @Column(name = "NAME", nullable=false)
    protected String name;

    @OneToMany(targetEntity = CategoryProductXrefImpl.class, mappedBy = "category", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @OrderBy(value="displayOrder")
    @BatchSize(size = 50)
    protected List<CategoryProductXref> allProductXrefs = new ArrayList<CategoryProductXref>(10);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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
    public List<CategoryProductXref> getAllProductXrefs() {
        return allProductXrefs;
    }

    @Override
    public void setAllProductXrefs(List<CategoryProductXref> allProductXrefs) {
        this.allProductXrefs = allProductXrefs;
    }
}
