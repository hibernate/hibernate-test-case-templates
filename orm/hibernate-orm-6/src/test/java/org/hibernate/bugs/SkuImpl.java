package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Persister;
import org.hibernate.persister.entity.JoinedSubclassEntityPersister;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Persister(impl = JoinedSubclassEntityPersister.class)
public class SkuImpl implements Sku{
    @Id
    private Long id;

    private String name;

    @ManyToOne(optional = true, targetEntity = ProductImpl.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "DEFAULT_PRODUCT_ID", foreignKey = @ForeignKey(NO_CONSTRAINT))
    protected Product defaultProduct;

    @ManyToOne(optional = true, targetEntity = ProductImpl.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ADDL_PRODUCT_ID")
    protected Product product;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Product getDefaultProduct() {
        return defaultProduct;
    }

    @Override
    public void setDefaultProduct(Product defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
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
