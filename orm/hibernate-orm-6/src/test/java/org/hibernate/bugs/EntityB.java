package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;

@Entity
public class EntityB implements IEntityB{
    @Id
    private long id;

    private String name;

    private BigDecimal number;

    @ManyToOne(
            optional = true,
            targetEntity = EntityA.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinColumn(
            name = "ADDL_PRODUCT_ID"
    )
    protected EntityA product;

    @OneToOne(
            optional = true,
            targetEntity = EntityA.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinColumn(
            name = "DEFAULT_PRODUCT_ID"
    )
    protected EntityA defaultProduct;

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

    public EntityA getProduct() {
        return product;
    }

    public void setProduct(EntityA product) {
        this.product = product;
    }

    public EntityA getDefaultProduct() {
        return defaultProduct;
    }

    public void setDefaultProduct(EntityA defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
