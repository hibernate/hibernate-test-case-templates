package org.hibernate.bugs;

import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeAbsOne;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeAbsThree;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeAbsTwo;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeFour;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeOne;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeThree;
import org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeTwo;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.SequenceMismatchStrategy;
import org.hibernate.id.enhanced.StandardOptimizerDescriptor;
import org.hibernate.loader.BatchFetchStyle;
import org.hibernate.query.NullPrecedence;

import org.hibernate.testing.bytecode.enhancement.BytecodeEnhancerRunner;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This always throws one of these two exceptions:
 * - Caused by: java.lang.IllegalStateException: Cannot access private org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeTwo org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeAbsOne.two from class org.hibernate.bugs.entityWithPrivateParameterizedProperty.AlternativeOne
 * - or some class cast exception for Junit Runners.
 *
 * Only because there are private PARAMETERIZED fields (not package private) in abstract AlternativeAbs* classes
 */
@RunWith(BytecodeEnhancerRunner.class) // This runner enables bytecode enhancement for your test.
public class MappedSuperclassParameterizedPRIVATEFieldQuarkusLikeORMUnitTestCase extends BaseCoreFunctionalTestCase {

    // Add your entities here.
    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] {
                AlternativeAbsOne.class,
                AlternativeAbsTwo.class,
                AlternativeAbsThree.class,
                AlternativeOne.class,
                AlternativeTwo.class,
                AlternativeThree.class,
                AlternativeFour.class
        };
    }

    // Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
    @Override
    protected void configure(Configuration configuration) {
        super.configure( configuration );

        // For your own convenience to see generated queries:
        configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
        configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
        //configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );

        // Other settings that will make your test case run under similar configuration that Quarkus is using by default:
        configuration.setProperty( AvailableSettings.PREFERRED_POOLED_OPTIMIZER, StandardOptimizerDescriptor.POOLED_LO.getExternalName() );
        configuration.setProperty( AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, "16" );
        configuration.setProperty( AvailableSettings.BATCH_FETCH_STYLE, BatchFetchStyle.PADDED.toString() );
        configuration.setProperty( AvailableSettings.QUERY_PLAN_CACHE_MAX_SIZE, "2048" );
        configuration.setProperty( AvailableSettings.DEFAULT_NULL_ORDERING, NullPrecedence.NONE.toString().toLowerCase( Locale.ROOT) );
        configuration.setProperty( AvailableSettings.IN_CLAUSE_PARAMETER_PADDING, "true" );
        configuration.setProperty( AvailableSettings.SEQUENCE_INCREMENT_SIZE_MISMATCH_STRATEGY, SequenceMismatchStrategy.NONE.toString() );

        // Add your own settings that are a part of your quarkus configuration:
        // configuration.setProperty( AvailableSettings.SOME_CONFIGURATION_PROPERTY, "SOME_VALUE" );
    }

    // Add your tests, using standard JUnit.
    @Test
    public void hhh123Test() throws Exception {
        // BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
        Session s = openSession();
        Transaction tx = s.beginTransaction();
        // Do stuff...
        tx.commit();
        s.close();
    }
}

