package org.hibernate.bugs;

import jakarta.persistence.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh19329Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...
		entityManager.createQuery("SELECT correlated_validation_detectedControls.id               AS ControlValidationView_id,\n" +
				"       correlated_validation_detectedControls.controlDomain.id AS ControlValidationView_controlDomainId,\n" +
				"       correlated_validation_detectedControls.control.id       AS ControlValidationView_controlId,\n" +
				"       correlated_validation_detectedControls.id               AS ControlValidationView_validationDetections,\n" +
				"       NULLFN(),\n" +
				"       validation_1.id\n" +
				"FROM Artifact artifact\n" +
				"         LEFT JOIN artifact.answers answers_1\n" +
				"         LEFT JOIN answers_1.question question_1\n" +
				"         LEFT JOIN artifact.attestations attestations_1\n" +
				"         LEFT JOIN attestations_1.referencedArtifact referencedArtifact_1\n" +
				"         LEFT JOIN referencedArtifact_1.validation validation_17\n" +
				"         LEFT JOIN artifact.validation validation_1\n" +
				"         JOIN ControlValidation correlated_validation_detectedControls ON (EXISTS (SELECT 1\n" +
				"                                                                                   FROM correlated_validation_detectedControls.artifactValidation _synth_subquery_0,\n" +
				"                                                                                        validation_1.artifact artifact_6\n" +
				"                                                                                   WHERE _synth_subquery_0.artifact = artifact_6))\n" +
				"WHERE artifact.id = :param_0").setParameter("param_0", 1L).getSingleResult();
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
