package org.hibernate.bugs.application;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.bugs.domain.model.Instrument;
import org.hibernate.bugs.domain.model.InstrumentLine;
import org.hibernate.bugs.port.adapter.persistence.hibernate.HibernateResultListTransformer;
import org.hibernate.bugs.port.adapter.persistence.hibernate.JoinOn;
import org.hibernate.jpa.spi.NativeQueryTupleTransformer;

import java.util.List;

public class InstrumentQueryService {

    @Transactional
    public List<InstrumentData> allInstruments(final Session session) {
        return session.createQuery("""
            select instrument.instrumentCode.id as code,
                   instrument.category as category,
                   instrument.description as description,
                   instrumentLines.key.id as line_keys_id,
                   instrumentLines.currencyCode.code as line_keys_currency_code,
                   instrumentLines.description as line_keys_description
              from Instrument instrument
              join instrument.instrumentLines instrumentLines
             order by code""")
                .setTupleTransformer(new NativeQueryTupleTransformer())
                .setResultListTransformer(new HibernateResultListTransformer(new JoinOn("code"), InstrumentData.class))
                .list();
    }
}
