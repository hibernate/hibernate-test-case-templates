package org.hibernate.bugs;

public interface SkuPriceData {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Sku getSku();

    void setSku(Sku sku);
}
