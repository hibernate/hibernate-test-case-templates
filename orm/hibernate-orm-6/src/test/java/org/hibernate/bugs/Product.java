package org.hibernate.bugs;

import java.util.List;

public interface Product {
    Long getId();

    void setId(Long id);

    Sku getDefaultSku();

    void setDefaultSku(Sku defaultSku);

    List<Sku> getAdditionalSkus();

    void setAdditionalSkus(List<Sku> additionalSkus);

    String getName();

    void setName(String name);
}
