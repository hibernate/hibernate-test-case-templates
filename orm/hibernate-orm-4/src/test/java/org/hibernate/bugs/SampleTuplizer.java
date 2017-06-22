package org.hibernate.bugs;

import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.AbstractComponentType;
import org.hibernate.type.CompositeType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

public class SampleTuplizer extends AbstractEntityTuplizer {
    private static final Object[] noArgs = new Object[0];

    public SampleTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo) {
        super(entityMetamodel, mappingInfo);
    }

    public EntityMode getEntityMode() {
        return EntityMode.MAP;
    }

    protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity) {
        Type type = null;
        if (mappedProperty.getValue() != null)
            type = mappedProperty.getType();
        return new SamplePropertyGetter(mappedProperty.getName(), type, mappedEntity.getEntityName());
    }

    protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity) {
        return new SamplePropertySetter(mappedProperty.getName());
    }

    protected Instantiator buildInstantiator(PersistentClass mappingInfo) {
        return new Sampleinstantiator(mappingInfo);
    }

    public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
        super.setIdentifier(entity, id, session);
    }

    public Serializable getIdentifier(Object entity, SessionImplementor session) {
        return super.getIdentifier(entity, session);
    }

    protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter) {
        return new SampleProxyFactory(getEntityName());
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

    protected Instantiator buildInstantiator(EntityBinding arg0) {
        return null;
    }

    @Override
    protected Getter buildPropertyGetter(AttributeBinding arg0) {
        return null;
    }

    @Override
    protected Setter buildPropertySetter(AttributeBinding arg0) {
        return null;
    }

    @Override
    protected ProxyFactory buildProxyFactory(EntityBinding arg0, Getter arg1, Setter arg2) {
        return null;
    }

    public static class Sampleinstantiator implements Instantiator {
        private String entityName;
        private boolean embeddedIdentifer = false;

        public Sampleinstantiator(PersistentClass mappingInfo) {
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

    public static class SampleProxyFactory implements ProxyFactory {
        private String entityName;

        public SampleProxyFactory(String entityName) {
            this.entityName = entityName;
        }

        public void postInstantiate(String entityName, Class persistentClass, Set interfaces, Method getIdentifierMethod, Method setIdentifierMethod, AbstractComponentType componentIdType) throws HibernateException {
        }

        public HibernateProxy getProxy(Serializable id, SessionImplementor session) throws HibernateException {
            return null;
        }

        @Override
        public void postInstantiate(String arg0, Class arg1, Set arg2, Method arg3, Method arg4, CompositeType arg5)
                throws HibernateException {
        }
    }
}
