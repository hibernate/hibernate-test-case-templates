package org.hibernate.bugs;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import org.hibernate.annotations.*;
import org.hibernate.persister.entity.JoinedSubclassEntityPersister;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Persister(impl = JoinedSubclassEntityPersister.class)
public class ProductImpl implements Product {
    @Id
    private Long id;

    private String name;

    @ManyToOne(targetEntity = SkuImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "DEFAULT_SKU_ID", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private Sku defaultSku;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = SkuImpl.class, mappedBy = "product",
            cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blProducts")
    @BatchSize(size = 50)
    protected List<Sku> additionalSkus = new ArrayList<Sku>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    public List<Sku> getAdditionalSkus() {
        return additionalSkus;
    }

    @Override
    public void setAdditionalSkus(List<Sku> additionalSkus) {
        this.additionalSkus = additionalSkus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
