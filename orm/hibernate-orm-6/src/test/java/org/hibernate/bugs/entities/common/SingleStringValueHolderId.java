package org.hibernate.bugs.entities.common;


import static org.hibernate.bugs.entities.common.IdGenerator.validateId;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SingleStringValueHolderId extends SingleStringValueHolder {

    protected SingleStringValueHolderId() {
        super();
    }

    protected SingleStringValueHolderId(IdPrefix idPrefix) {
        super(IdGenerator.generate(idPrefix));
    }

    protected SingleStringValueHolderId(IdPrefix idPrefix, String value) {
        super(value);
        validateId(idPrefix, value);
    }

    public static <T> T newId(Class<T> baseclass) {
        return createInstance(baseclass);
    }

    protected static <T> T createInstance(Class<T> baseclass) {
        try {
            var declaredConstructor = baseclass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
