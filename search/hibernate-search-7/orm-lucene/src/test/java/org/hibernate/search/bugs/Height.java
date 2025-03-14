package org.hibernate.search.bugs;

import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

public class Height {

    @GenericField(sortable = Sortable.YES, projectable = Projectable.YES)
    private final Long value;

    public Height(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
