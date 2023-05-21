/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.hibernate.tool.schema.Action;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.hibernate.type.SqlTypes;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCaseHhh16360PostgreSQL extends BaseCoreFunctionalTestCase {

    @Entity
    public class EntityWithVarchar {
        @Id
        @GeneratedValue
        Long id;
        String topic;
    }

    @Entity
    public class EntityWithText {
        @Id
        @GeneratedValue
        Long id;
        @JdbcTypeCode(SqlTypes.LONG32VARCHAR)
        String contents;

    }

    @Entity
    public class EntityWithDate {
        @Id
        @GeneratedValue
        Long id;
        Date aDate = new Date();
    }

    @Entity
    public class EntityWithDouble {
        @Id
        @GeneratedValue
        Long id;
        Double aDouble = 0.0;
    }

    // Add your entities here.
    @Override
    protected Class[] getAnnotatedClasses() {
        return new Class[]{
                EntityWithVarchar.class,
                EntityWithDate.class,
                EntityWithText.class,
                EntityWithDouble.class
        };
    }

    // Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
    @Override
    protected void configure(Configuration configuration) {
        super.configure( configuration );
        configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
        configuration.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty(AvailableSettings.DRIVER, "org.postgresql.Driver");
        configuration.setProperty(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty(AvailableSettings.USER, "postgres");
        configuration.setProperty(AvailableSettings.PASS, "postgres");
        configuration.setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.CREATE_DROP.getExternalHbm2ddlName());
    }

    // Add your tests, using standard JUnit.
    @Test
    public void testDate() throws Exception {
        StringWriter updateScript = new StringWriter();

        Map<String, Object> properties = new HashMap<>(sessionFactory().getProperties());
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.NONE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_ACTION, Action.UPDATE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_CREATE_TARGET, updateScript);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_CREATE_SCHEMAS, false);

        Metadata metadata = new MetadataSources(serviceRegistry())
                .addAnnotatedClass(EntityWithDate.class)
                .buildMetadata();

        SchemaManagementToolCoordinator.process(
                metadata,
                serviceRegistry(),
                properties,
                action -> {
                }
        );

        var expected = "";
        var actual = updateScript.toString();

        Assert.assertEquals("UpdateScript after HDM2DDL=create should be empty.", expected, actual);
    }

    @Test
    public void testDouble() throws Exception {
        StringWriter updateScript = new StringWriter();

        Map<String, Object> properties = new HashMap<>(sessionFactory().getProperties());
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.NONE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_ACTION, Action.UPDATE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_CREATE_TARGET, updateScript);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_CREATE_SCHEMAS, false);

        Metadata metadata = new MetadataSources(serviceRegistry())
                .addAnnotatedClass(EntityWithDouble.class)
                .buildMetadata();

        SchemaManagementToolCoordinator.process(
                metadata,
                serviceRegistry(),
                properties,
                action -> {
                }
        );

        var expected = "";
        var actual = updateScript.toString();

        Assert.assertEquals("UpdateScript after HDM2DDL=create should be empty.", expected, actual);
    }

    @Test
    public void testVarchar() throws Exception {
        StringWriter updateScript = new StringWriter();

        Map<String, Object> properties = new HashMap<>(sessionFactory().getProperties());
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.NONE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_ACTION, Action.UPDATE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_CREATE_TARGET, updateScript);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_CREATE_SCHEMAS, false);

        Metadata metadata = new MetadataSources(serviceRegistry())
                .addAnnotatedClass(EntityWithVarchar.class)
                .buildMetadata();

        SchemaManagementToolCoordinator.process(
                metadata,
                serviceRegistry(),
                properties,
                action -> {
                }
        );

        var expected = "";
        var actual = updateScript.toString();

        Assert.assertEquals("UpdateScript after HDM2DDL=create should be empty.", expected, actual);
    }

    @Test
    public void testText() throws Exception {
        StringWriter updateScript = new StringWriter();

        Map<String, Object> properties = new HashMap<>(sessionFactory().getProperties());
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.NONE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_ACTION, Action.UPDATE);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_SCRIPTS_CREATE_TARGET, updateScript);
        properties.put(AvailableSettings.JAKARTA_HBM2DDL_CREATE_SCHEMAS, false);

        Metadata metadata = new MetadataSources(serviceRegistry())
                .addAnnotatedClass(EntityWithText.class)
                .buildMetadata();

        SchemaManagementToolCoordinator.process(
                metadata,
                serviceRegistry(),
                properties,
                action -> {
                }
        );

        var expected = "";
        var actual = updateScript.toString();

        Assert.assertEquals("UpdateScript after HDM2DDL=create should be empty.", expected, actual);
    }
}
