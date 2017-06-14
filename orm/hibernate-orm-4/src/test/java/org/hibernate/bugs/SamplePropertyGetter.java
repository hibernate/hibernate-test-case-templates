package org.hibernate.bugs;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.type.Type;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by speed on 14/6/17.
 */
public class SamplePropertyGetter implements Getter {
    private String propertyName;
    private Type propertyType;
    private String entityName;

    public SamplePropertyGetter(String propertyName, Type propertyType, String entityName) {
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

    public Object getForInsert(Object owner, Map mergeMap, SessionImplementor session) throws HibernateException {
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
        return null;
    }
}