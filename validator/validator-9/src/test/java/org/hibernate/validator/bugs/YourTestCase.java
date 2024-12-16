package org.hibernate.validator.bugs;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.pathWith;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.violationOf;

import java.util.Set;

import org.hibernate.validator.testutil.TestForIssue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;

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
		assertThat( constraintViolations )
				.containsOnlyViolations( violationOf( NotNull.class )
						.withMessage( "must not be null" )
						.withPropertyPath( pathWith().property( "id" ) ) );
	}

}
