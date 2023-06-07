package org.hibernate.bugs;

import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class EntityRelationSequenceStyleGenerator extends SequenceStyleGenerator {

    @Override
    protected QualifiedName determineSequenceName(Properties params, Dialect dialect, JdbcEnvironment jdbcEnv,
                                                  ServiceRegistry serviceRegistry) {

        return new QualifiedNameParser.NameParts(
                null,
                null,
                jdbcEnv.getIdentifierHelper().toIdentifier("sequence.name")
        );
    }
}