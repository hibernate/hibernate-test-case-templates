package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
public class SkuPriceDataImpl implements SkuPriceData {
    @Id
    private Long id;

    private String name;

    @ManyToOne(
            cascade = {CascadeType.REFRESH},
            targetEntity = SkuImpl.class,
            optional = false
    )
    @JoinColumn(
            name = "SKU_ID"
    )
    private Sku sku;

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
    public Sku getSku() {
        return sku;
    }

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }
}
