package org.hibernate.bugs;

import java.math.BigDecimal;

public interface CategoryProductXref {
    Long getId();

    void setId(Long id);

    Category getCategory();

    void setCategory(Category category);

    Product getProduct();

    void setProduct(Product product);

    BigDecimal getDisplayOrder();

    void setDisplayOrder(BigDecimal displayOrder);
}
