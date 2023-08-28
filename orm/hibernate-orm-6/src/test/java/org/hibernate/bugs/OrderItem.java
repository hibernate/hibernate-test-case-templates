package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Persister;
import org.hibernate.persister.entity.JoinedSubclassEntityPersister;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Persister(impl = JoinedSubclassEntityPersister.class)
public class OrderItem {
    @Id
    private Long id;

    @ManyToOne(targetEntity = SkuImpl.class, fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "SKU_ID", nullable = false)
    private Sku sku;

    @ManyToOne(
            targetEntity = OrderEntity.class
    )
    @JoinColumn(
            name = "ORDER_ID"
    )
    OrderEntity order;

    @ManyToOne(
            targetEntity = ProductImpl.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "PRODUCT_ID"
    )
    protected Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
