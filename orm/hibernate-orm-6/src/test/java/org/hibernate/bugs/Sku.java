package org.hibernate.bugs;

public interface Sku {
    Long getId();

    void setId(Long id);

    Product getDefaultProduct();

    void setDefaultProduct(Product defaultProduct);

    Product getProduct();

    void setProduct(Product product);

    String getName();

    void setName(String name);
}
