package org.hibernate.bugs;

/**
 * Created by speed on 13/6/17.
 */

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.internal.MapMember;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.type.Type;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

;

public class SampleTestPropertyGetter implements Getter {
    private String propertyName;
    private Type propertyType;
    private String entityName;

    public SampleTestPropertyGetter(String propertyName, Type propertyType, String entityName) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.entityName = entityName;
    }

    public Object get(Object owner) throws HibernateException {
        try {
            return null;
        } catch (Throwable throwable) {
            throw new HibernateException("Property : " + propertyName + " - " + throwable.getMessage(), throwable);
        }
    }

    public Object getForInsert(Object owner, Map mergeMap, SharedSessionContractImplementor session) throws HibernateException {
        return get(owner);
    }

    public Class getReturnType() {
        return Object.class;
    }

    public String getMethodName() {
        return null;
    }

    public Method getMethod() {
        return null;
    }

    public Member getMember() {
        return new MapMember(propertyName, propertyType.getClass());
    }
}

