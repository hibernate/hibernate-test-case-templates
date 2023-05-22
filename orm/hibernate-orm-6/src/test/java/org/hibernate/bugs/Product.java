package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

public interface Product {


    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Sku getDefaultSku();

    void setDefaultSku(Sku defaultSku);

    List<CategoryProductXref> getAllParentCategoryXrefs();

    void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategoryXrefs);
}
