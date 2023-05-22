package org.hibernate.bugs;

import java.util.List;

public interface Category {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    List<CategoryProductXref> getAllProductXrefs();

    void setAllProductXrefs(List<CategoryProductXref> allProductXrefs);
}
