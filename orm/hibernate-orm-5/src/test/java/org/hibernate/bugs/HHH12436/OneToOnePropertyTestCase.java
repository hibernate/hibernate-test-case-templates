package org.hibernate.bugs.HHH12436;

import javax.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * In case of Hibernate version
 * <ul>
 * <li>5.2.14.Final</li>
 * <li>5.2.15.Final</li>
 * <li>5.2.16.Final</li>
 * </ul>
 * this test will fail with
 * <pre>
 * org.hibernate.id.IdentifierGenerationException: attempted to assign id from null one-to-one property [models.Secunda.parent]
 * </pre>
 *
 * Good versions are
 * <ul>
 * <li>5.2.12.Final</li>
 * <li>5.2.13.Final</li>
 * </ul>
 *
 * @author localEvg
 */
public class OneToOnePropertyTestCase {

    private SessionFactory sf;

    @Before
    public void setup() {
        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
                // Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update");

        Metadata metadata = new MetadataSources(srb.build())
                // Add your entities here.
                .addAnnotatedClass(Prima.class)
                .addAnnotatedClass(Secunda.class)
                .buildMetadata();

        sf = metadata.buildSessionFactory();
    }

    // Add your tests, using standard JUnit.
    @Test
    public void hhh123Test() throws Exception {
        EntityManager em = sf.createEntityManager();

        // prepare
        em.getTransaction().begin();
        Long primaId;
        {
            Prima prim = new Prima();
            prim.setOptionalData(null); // <-- main line

            em.persist(prim);

            primaId = prim.getId();
            Assert.assertNotNull(primaId);
        }
        em.getTransaction().commit();

        // TEST
        em.getTransaction().begin();
        {
            // emulate object recieved from json
            Prima prim = new Prima();
            prim.setId(primaId);
            {
                Secunda sec = new Secunda();
                sec.setParent(prim); // <-- not null !
                prim.setOptionalData(sec);
            }
            Prima mergedPrima = em.merge(prim); // <-- exception here
            Assert.assertNotNull(mergedPrima);
            em.persist(mergedPrima);
        }
        em.getTransaction().commit();

        em.close();

    }
}
