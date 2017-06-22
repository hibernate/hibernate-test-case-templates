package org.hibernate.bugs;

import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.CompositeType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

public class SampleTestTuplizer extends AbstractEntityTuplizer {
    private static final Object[] noArgs = new Object[0];

    public SampleTestTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo) {
        super(entityMetamodel, mappingInfo);
    }

    public EntityMode getEntityMode() {
        return EntityMode.MAP;
    }

    protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity) {
        Type type = null;
        if (mappedProperty.getValue() != null)
            type = mappedProperty.getType();
        return new SampleTestPropertyGetter(mappedProperty.getName(), type, mappedEntity.getEntityName());
    }

    protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity) {
        return new SampleTestPropertySetter(mappedProperty.getName());
    }

    protected Instantiator buildInstantiator(PersistentClass mappingInfo) {
        return new SampleTestInstantiator(mappingInfo);
    }

    public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
        super.setIdentifier(entity, id, session);
    }

    public Serializable getIdentifier(Object entity, SessionImplementor session) {
        return super.getIdentifier(entity, session);
    }

    protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter) {
        return new SampleTestProxyFactory(getEntityName());
    }

    public Class getConcreteProxyClass() {
        return null;
    }

    public boolean isInstrumented() {
        return false;
    }

    public Class getMappedClass() {
        return null;
    }

    public EntityNameResolver[] getEntityNameResolvers() {
        return null;
    }

    public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
        return null;
    }

    @Override
    protected Instantiator buildInstantiator(EntityMetamodel metamodel, PersistentClass mappingInfo) {
        return new SampleTestInstantiator(mappingInfo);
    }

    public static class SampleTestInstantiator implements Instantiator {
        private String entityName;
        private boolean embeddedIdentifer = false;

        public SampleTestInstantiator(PersistentClass mappingInfo) {
            this.entityName = mappingInfo.getEntityName();
            embeddedIdentifer = mappingInfo.hasEmbeddedIdentifier();
        }

        public Object instantiate(Serializable id) {
            Object obj = instantiate();
            return obj;
        }

        public Object instantiate() {
            return null;
        }

        public boolean isInstance(Object object) {
            return false;
        }
    }

    public static class SampleTestProxyFactory implements ProxyFactory {
        private String entityName;

        public SampleTestProxyFactory(String entityName) {
            this.entityName = entityName;
        }

        public HibernateProxy getProxy(Serializable id, SharedSessionContractImplementor session) throws HibernateException {
            return null;
        }

        @Override
        public void postInstantiate(String arg0, Class arg1, Set arg2, Method arg3, Method arg4, CompositeType arg5)
                throws HibernateException {
        }
    }
}
