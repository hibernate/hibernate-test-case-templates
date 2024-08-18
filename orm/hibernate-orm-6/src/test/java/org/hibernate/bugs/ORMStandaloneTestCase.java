package org.hibernate.bugs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.bugs.datamodel.ATest;
import org.hibernate.bugs.datamodel.Submission;
import org.hibernate.bugs.datamodel.Task;
import org.hibernate.bugs.datamodel.TestCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ORMStandaloneTestCase {
	private SessionFactory sf;

	@BeforeEach
	void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
				// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
				.applySetting("hibernate.show_sql", "true")
				.applySetting("hibernate.format_sql", "true")
				.applySetting("hibernate.hbm2ddl.auto", "update");

		Metadata metadata = new MetadataSources(srb.build())
				// Add your entities here.
				.addAnnotatedClass(Task.class)
				.addAnnotatedClass(Submission.class)
				.addAnnotatedClass(ATest.class)
				.addAnnotatedClass(TestCount.class)
				.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	@Test
	void hhh18501Test() throws Exception {
		Session session = sf.openSession();
		{
			session.getTransaction().begin();
			Task task = new Task();
			session.persist(task);
			session.persist(new Submission(task));
			session.getTransaction().commit();
		}
		Submission submission = session.get(Submission.class, 1);
		session.getTransaction().begin();
		ATest test = new ATest();
		test.setTask(submission.getTask());
		session.persist(test);
		session.getTransaction().commit();
		session.getTransaction().begin();
		test.setValue(5);
		session.getTransaction().commit();
		{ // as soon as this block is inserted, we get an IllegalStateException at the last commit
			session.getTransaction().begin();
			TestCount a = new TestCount();
			a.setTest(test); // this reference seems to be the culprit
			session.persist(a);
			session.getTransaction().commit();
		}
		session.getTransaction().begin();
		session.remove(test);
		session.getTransaction().commit(); // fails here
	}
}
