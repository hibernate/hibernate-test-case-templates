package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
public class SkuImpl implements Sku{

    @Id
    @Column(name = "SKU_ID")
    private Long id;

    private String name;

    @OneToOne(optional = true, targetEntity = ProductImpl.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "DEFAULT_PRODUCT_ID")
    protected Product defaultProduct;


    @Embedded
    private PriceListSkuImpl priceListSku;

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
    public PriceListSkuImpl getPriceListSku() {
        if(priceListSku == null){
            priceListSku = new PriceListSkuImpl();
        }
        return priceListSku;
    }
    @Override
    public void setPriceListSku(PriceListSkuImpl priceListSku) {
        this.priceListSku = priceListSku;
    }
    @Override
    public Product getDefaultProduct() {
        return defaultProduct;
    }
    @Override
    public void setDefaultProduct(Product defaultProduct) {
        this.defaultProduct = defaultProduct;
    }
}
