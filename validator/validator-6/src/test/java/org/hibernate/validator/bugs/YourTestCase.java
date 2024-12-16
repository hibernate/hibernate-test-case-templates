package org.hibernate.validator.bugs;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.testutil.TestForIssue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class YourTestCase {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@TestForIssue(jiraKey = "HV-NNNNN") // Please fill in the JIRA key of your issue
	void testYourBug() {
		YourAnnotatedBean yourEntity1 = new YourAnnotatedBean( null, "example" );

		Set<ConstraintViolation<YourAnnotatedBean>> constraintViolations = validator.validate( yourEntity1 );
		assertEquals( 1, constraintViolations.size() );
		assertEquals(
				"must not be null",
				constraintViolations.iterator().next().getMessage()
		);
	}

}
