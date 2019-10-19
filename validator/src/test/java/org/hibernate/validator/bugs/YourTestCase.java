package org.hibernate.validator.bugs;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.testutil.DummyTraversableResolver;
import org.hibernate.validator.testutil.TestForIssue;
import org.junit.BeforeClass;
import org.junit.Test;

public class YourTestCase {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {

		ValidatorFactory factory = Validation.byDefaultProvider()
											 .configure()
											 // I need caching traversable resolver
											 .traversableResolver(new DummyTraversableResolver())
											 .buildValidatorFactory();


		validator = factory.getValidator();

	}

	@Test
	@TestForIssue(jiraKey = "HV-1692") // Please fill in the JIRA key of your issue
	public void testYourBug() {
		YourAnnotatedBean yourEntity1 = new YourAnnotatedBean( );
		AnotherBean anotherBean = new AnotherBean();
		anotherBean.setYourAnnotatedBean(yourEntity1);
		yourEntity1.setBean(anotherBean);

		Set<ConstraintViolation<YourAnnotatedBean>> constraintViolations = validator.validate( yourEntity1);
		assertEquals( 0, constraintViolations.size() );
	}

}
