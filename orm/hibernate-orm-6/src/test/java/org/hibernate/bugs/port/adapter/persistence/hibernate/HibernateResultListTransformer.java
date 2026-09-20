package org.hibernate.bugs.port.adapter.persistence.hibernate;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.hibernate.jpa.spi.NativeQueryTupleTransformer;
import org.hibernate.query.ResultListTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * {@link HibernateResultListTransformer} is a utility for mapping Hibernate query results
 * (as {@link Tuple}s) to Java records or classes using constructor-based mapping. It is
 * designed to work with queries that use SQL-style aliases, automatically transforming
 * those aliases into Java naming conventions and grouping related fields into nested structures.
 * </p>
 * <br>
 * <b>Alias Mapping and Java Records</b>
 * <p>
 * This transformer maps each query alias to the corresponding constructor parameter in the
 * target Java record or class. It supports alias transformation from SQL-style (snake_case)
 * to Java-style (camelCase). For example, a query alias like {@code line_keys_id} will be
 * mapped to the id field of a nested record named {@code lineKeys} in the target record,
 * as shown below:
 * </p>
 * <pre class="code">
 * public record InstrumentData(
 *     String code,
 *     String categoryCode,
 *     String description,
 *     List&ltLineKeyData&gt lineKeys) {
 *
 *     public record LineKeyData(
 *         String id,
 *         String countryCode,
 *         String currencyCode) {}
 * }
 * </pre>
 * <p>Given the following query:</p>
 * <pre class="code">
 * select instrument.instrumentCode.id as code,
 *        instrument.instrument.category.code as category_code,
 *        instrument.instrument.description as description,
 *        instrumentLines.lineKey.id as line_keys_id,
 *        instrumentLines.country.code as line_keys_country_code,
 *        instrumentLines.currency.code as line_keys_currency_code
 *   from Instrument instrument
 *   join instrument.lines instrumentLines
 *  order by code
 * </pre>
 * <p>
 * The transformer will group all fields prefixed with {@code line_keys_} into the
 * {@code lineKeys} list, mapping each subfield to the corresponding property in {@code LineKeyData}.
 * </p>
 * <br>
 * <b>Importance of Ordering and JoinOn</b>
 * <p>
 * The {@link JoinOn} parameter (e.g., {@code new JoinOn("code")}) specifies the field
 * used to group related rows (such as multiple lineKeys per instrument). The query <b>must</b>
 * be ordered by this field ({@code order by code}) to ensure correct grouping, as the
 * transformer iterates through the result set in order and groups rows based on the {@link JoinOn} alias.
 * </p>
 * <p>
 * {@link JoinOn} also supports multiple fields, allowing for more complex grouping scenarios,
 * when the natural id is composite for instance. In this case, the query should be ordered by
 * all fields specified in the {@link JoinOn}.
 * </p>
 * <br>
 * <b>Tuple Transformation Requirement</b>
 * <p>
 * Due to Hibernate 6's separation of tuple and result list transformers, you must use
 * {@link NativeQueryTupleTransformer} before {@link HibernateResultListTransformer}. This ensures
 * the query returns results as {@link Tuple} objects, which the transformer expects.
 * </p>
 * <br>
 * <b>Example Usage</b>
 * <pre class="code">
 * .setTupleTransformer(new NativeQueryTupleTransformer())
 * .setResultListTransformer(new HibernateResultListTransformer<>(new JoinOn("code"), InstrumentData.class))
 * </pre>
 * <p>This pattern is required for correct mapping and grouping of query results into Java records.</p>
 * <br>
 * <b>Summary:</b>
 * <ul>
 *     <li>Maps Hibernate query results to Java records or classes using constructor-based mapping.</li>
 *     <li>Groups related fields into nested structures based on prefixes (e.g., {@code line_keys_*} into {@code lineKeys}).</li>
 *     <li>Requires ordering by the {@link JoinOn} field(s) to ensure correct grouping.</li>
 *     <li>Must be used after {@link NativeQueryTupleTransformer} to work with {@link Tuple} results.</li>
 * </ul>
 * <p>This class is non thread-safe</p>
 */

public final class HibernateResultListTransformer<T> implements ResultListTransformer<T> {

    private final JoinOn joinOn;
    private final Class<T> resultType;

    public HibernateResultListTransformer(
            final JoinOn joinOn,
            final Class<T> resultType) {
        this.resultType = resultType;
        this.joinOn = joinOn;
    }

    @Override
    public List<T> transformList(final List collection) {
        try {
            final List<T> resultList = new ArrayList<>(collection.size());
            final ResultCursor resultCursor = new ResultCursor(collection);

            while (resultCursor.next()) {
                HibernateObjectMapper<T> mapper = new HibernateObjectMapper<>(
                        joinOn,
                        resultCursor,
                        resultType);

                resultList.add(mapper.mapResultToType());
            }

            return resultList;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to query object.", e);
        }
    }
    static class ResultCursor {

        private final ListIterator<Tuple> iterator;
        private Tuple current;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public ResultCursor(final List list) {
            this.iterator = list.listIterator();
        }

        public Object get(final String alias) {
            return this.current.get(alias);
        }

        public boolean next() {
            if (this.iterator.hasNext()) {
                this.current = this.iterator.next();
                return true;
            }
            return false;
        }

        public void previous() {
            this.current = this.iterator.previous();
        }

        public List<TupleElement<?>> getElements() {
            return this.current.getElements();
        }
    }

}