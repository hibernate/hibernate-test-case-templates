package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;

public interface Sku {
    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public PriceListSkuImpl getPriceListSku();

    public void setPriceListSku(PriceListSkuImpl priceListSku);

    public Product getDefaultProduct();

    public void setDefaultProduct(Product defaultProduct);
}
