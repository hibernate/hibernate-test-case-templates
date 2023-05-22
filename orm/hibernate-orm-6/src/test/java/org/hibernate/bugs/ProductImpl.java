package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductImpl implements Product{

    @Id
    private Long id;

    private String name;

    @OneToOne(targetEntity = SkuImpl.class, cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blProducts")
    @JoinColumn(name = "DEFAULT_SKU_ID")
    protected Sku defaultSku;

    @OneToMany(targetEntity = CategoryProductXrefImpl.class, mappedBy = "product",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @OrderBy(value = "displayOrder")
    @BatchSize(size = 50)
    protected List<CategoryProductXref> allParentCategoryXrefs = new ArrayList<CategoryProductXref>();


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
    public Sku getDefaultSku() {
        return defaultSku;
    }

    @Override
    public void setDefaultSku(Sku defaultSku) {
        this.defaultSku = defaultSku;
    }

    @Override
    public List<CategoryProductXref> getAllParentCategoryXrefs() {
        return allParentCategoryXrefs;
    }

    @Override
    public void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategoryXrefs) {
        this.allParentCategoryXrefs = allParentCategoryXrefs;
    }
}
