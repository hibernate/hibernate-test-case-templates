package org.hibernate.validator.bugs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.testutil.TestForIssue;
import org.junit.BeforeClass;
import org.junit.Test;

public class YourTestCase {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@TestForIssue(jiraKey = "HV-1831") // Please fill in the JIRA key of your issue
	public void testYourBug() {
		List<FirstChildBean> firstChildren = createFirstChildren();

		ParentAnnotatedBean yourEntity1 = new ParentAnnotatedBean( "parent-bean", firstChildren );

		Set<ConstraintViolation<ParentAnnotatedBean>> constraintViolations = validator.validate( yourEntity1 );
		assertEquals( 0, constraintViolations.size() );
	}

	private List<FirstChildBean> createFirstChildren() {
		List<FirstChildBean> firstChildren = new ArrayList<>();

		for(int i = 0; i < 50; i++) {
			firstChildren.add(new FirstChildBean("foo" + i, createSecondChildren()));
		}

		return firstChildren;
	}

	private List<SecondChildBean> createSecondChildren() {
		List<SecondChildBean> secondChildren = new ArrayList<>();

		for(int i = 0; i < 10; i++) {
			secondChildren.add(new SecondChildBean("foo" + i, "bar" + i));
		}

		return secondChildren;
	}

}
