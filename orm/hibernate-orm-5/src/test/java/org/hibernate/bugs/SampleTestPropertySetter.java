package org.hibernate.bugs;

/**
 * Created by speed on 13/6/17.
 */

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.spi.Setter;

import java.lang.reflect.Method;

public class SampleTestPropertySetter implements Setter {
    private String propertyName;
    private String setter;

    public SampleTestPropertySetter(String propertyName) {
        this.propertyName = propertyName;
        this.setter = "set" + propertyName;
    }

    public void set(Object target, Object value, SessionFactoryImplementor factory) throws HibernateException {
    }

    public String getMethodName() {
        return null;
    }

    public Method getMethod() {
        return null;
    }
}
