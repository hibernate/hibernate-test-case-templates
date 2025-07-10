package org.hibernate.bugs.port.adapter.persistence.hibernate;

import jakarta.persistence.TupleElement;
import org.hibernate.bugs.port.adapter.persistence.hibernate.HibernateResultListTransformer.ResultCursor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.*;

final class HibernateObjectMapper<T> {

    private final JoinOn joinOn;
    private final ResultCursor resultCursor;
    private final String aliasPrefix;
    private final Class<? extends T> resultType;

    HibernateObjectMapper(
            final JoinOn joinOn,
            final ResultCursor resultCursor,
            final Class<? extends T> resultType) {

        this.joinOn = joinOn;
        this.resultCursor = resultCursor;
        this.resultType = resultType;
        this.aliasPrefix = null;
    }

    HibernateObjectMapper(
            final JoinOn joinOn,
            final ResultCursor resultCursor,
            final String aliasPrefix,
            final Class<? extends T> resultType) {

        this.joinOn = joinOn;
        this.resultCursor = resultCursor;
        this.resultType = resultType;
        this.aliasPrefix = aliasPrefix;
    }

    T mapResultToType() {

        final Map<String, Parameter> associationsToMap = new HashMap<>();

        final Parameter[] parameters = this.resultTypeParameters();

        final List<Object> parameterValues = new ArrayList<>(parameters.length);

        for (Parameter parameter : parameters) {
            String aliasName = this.parameterToAliasName(parameter.getName());

            if (this.hasAlias(this.cursor(), aliasName)) {
                Object parameterValue = this.parameterValueFor(aliasName, parameter.getType());

                parameterValues.add(parameterValue);
            } else {
                String objectPrefix = this.toObjectPrefix(aliasName);

                if (!associationsToMap.containsKey(objectPrefix) &&
                        this.hasAssociation(this.cursor(), objectPrefix)) {

                    associationsToMap.put(parameter.getName(), parameter);
                }
            }
        }

        if (!associationsToMap.isEmpty()) {
            this.mapAssociations(parameterValues, this.cursor(), associationsToMap.values());
        }

        return this.createFrom(parameterValues);
    }

    private String aliasPrefix() {
        return this.aliasPrefix;
    }

    private boolean hasAliasPrefix() {
        return this.aliasPrefix != null;
    }

    private Object parameterValueFor(final String aliasName, final Class<?> type) {
        Object value;

        try {
            value = this.cursor().get(aliasName);

            if (value == null && type.isPrimitive()) {
                throw new IllegalArgumentException("Cannot assign null value to primitive type.");
            }

            if (value != null && !type.isAssignableFrom(value.getClass())) {
                throw new IllegalArgumentException("Cannot assign " + value.getClass() + " to " + type);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to map " + aliasName, e);
        }

        return value;
    }

    private Parameter[] resultTypeParameters() {
        return this.resultType().getDeclaredConstructors()[0].getParameters();
    }

    private Collection<Object> createCollectionFrom(final Class<?> type) {
        Collection<Object> newCollection = null;

        if (List.class.isAssignableFrom(type)) {
            newCollection = new ArrayList<>();
        } else if (Set.class.isAssignableFrom(type)) {
            newCollection = new HashSet<>();
        }

        return newCollection;
    }

    @SuppressWarnings("unchecked")
    private T createFrom(final List<Object> parameterValues) {
        try {
            return (T) this.resultType().getDeclaredConstructors()[0].newInstance(parameterValues.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Unable to create instance of: " + this.resultType().getName(), e);
        }
    }

    private String parameterToAliasName(final String parameterName) {
        final StringBuilder builder = new StringBuilder();

        if (this.hasAliasPrefix()) {
            builder.append(this.aliasPrefix());
        }

        for (char ch : parameterName.toCharArray()) {
            if (Character.isAlphabetic(ch) && Character.isUpperCase(ch)) {
                builder.append('_').append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    private boolean hasAssociation(final ResultCursor resultCursor, final String objectPrefix) {
        try {

            for (TupleElement<?> metaData : resultCursor.getElements()) {
                String aliasName = metaData.getAlias();

                if (aliasName.startsWith(objectPrefix) &&
                        this.joinOn().isJoinedOn(resultCursor)) {

                    return true;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read metadata.", e);
        }

        return false;
    }

    private boolean hasAlias(final ResultCursor resultCursor, final String aliasName) {
        try {
            resultCursor.get(aliasName);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JoinOn joinOn() {
        return this.joinOn;
    }

    private void mapAssociations(
            final List<Object> parameterValues,
            final ResultCursor resultCursor,
            final Collection<Parameter> associationsToMap) {

        final Map<String, Collection<Object>> mappedCollections = new HashMap<>();

        String currentAssociationName = null;

        try {
            this.joinOn().saveCurrentLeftQualifier(resultCursor);

            for (boolean hasResult = true; hasResult; hasResult = resultCursor.next()) {

                if (!this.joinOn().hasCurrentLeftQualifier(resultCursor)) {
                    resultCursor.previous();

                    return;
                }

                for (Parameter associationParameter : associationsToMap) {

                    currentAssociationName = associationParameter.getName();

                    Class<?> associationParameterType;

                    Collection<Object> collection = null;

                    if (Collection.class.isAssignableFrom(associationParameter.getType())) {
                        collection = mappedCollections.get(associationParameter.getName());

                        if (collection == null) {
                            collection = this.createCollectionFrom(associationParameter.getType());
                            mappedCollections.put(associationParameter.getName(), collection);
                            parameterValues.add(collection);
                        }

                        ParameterizedType parameterizedType = (ParameterizedType) associationParameter.getParameterizedType();
                        associationParameterType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

                    } else {
                        associationParameterType = associationParameter.getType();
                    }

                    String aliasName = this.parameterToAliasName(associationParameter.getName());

                    HibernateObjectMapper<Object> mapper =
                            new HibernateObjectMapper<>(
                                    this.joinOn(),
                                    this.cursor(),
                                    this.toObjectPrefix(aliasName),
                                    associationParameterType);

                    Object associationObject = mapper.mapResultToType();

                    if (collection != null) {
                        collection.add(associationObject);
                    } else {
                        parameterValues.add(associationObject);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to map object association for " + currentAssociationName, e);
        }
    }

    private ResultCursor cursor() {
        return this.resultCursor;
    }

    private Class<? extends T> resultType() {
        return this.resultType;
    }

    private String toObjectPrefix(final String aliasName) {
        return aliasName + "_";
    }
}
