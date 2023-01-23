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

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.bugs.domain.CriterionClause;
import org.hibernate.bugs.domain.StudyEligibilityCriterion;
import org.hibernate.bugs.domain.questionnaire.QuestionnaireBuilder;
import org.hibernate.bugs.domain.questionnaire.QuestionBuilder;
import org.hibernate.bugs.domain.questionnaire.ScreeningQuestion;
import org.hibernate.bugs.domain.questionnaire.ScreeningQuestionOption;
import org.hibernate.bugs.domain.questionnaire.ScreeningQuestionnaire;
import org.hibernate.bugs.domain.Study;
import org.hibernate.bugs.domain.questionnaire.StudyScreeningQuestion;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 *
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				Study.class,
				ScreeningQuestion.class,
				ScreeningQuestionOption.class,
				ScreeningQuestionnaire.class,
				StudyScreeningQuestion.class,
				StudyEligibilityCriterion.class,
				CriterionClause.class
		};
	}

	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	private long studyId;
	private ScreeningQuestionnaire qnaire;

	@Before
	public void setup(){
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		Study study = new Study();

		CriterionClause clause1 = new CriterionClause("clause1", 1);
		CriterionClause clause2 = new CriterionClause("clause2",2);

		Set<StudyEligibilityCriterion> eligibilityCriteria = new HashSet<>();
		StudyEligibilityCriterion e = new StudyEligibilityCriterion(1, "Criteria 1", study);
		List<CriterionClause> clauses = new ArrayList<>();
		clauses.add(clause1);
		clauses.add(clause2);
		e.setClauses(clauses);
		eligibilityCriteria.add(e);

		eligibilityCriteria.add(new StudyEligibilityCriterion(2,"Criteria 2",study));

		studyId = (Long)s.save(study);
		study.setEligibilityCriteria(eligibilityCriteria);

		qnaire = QuestionnaireBuilder.create(new Study(studyId))
																 .addQuestion("first question")
																 .addOptions("question1option1")
																 .buildQuestion()
																 .addQuestion("second question")
																 .addOptions("question2option1", "question2option2")
																 .buildQuestion()
																 .build();

		s.save(qnaire);
		tx.commit();
		s.close();
	}

	@Test
	public void hhh16111_create() {
		final ScreeningQuestionnaire actual = getQnaire(studyId);

		assertEquals(0, actual.getVersion());

		assertEquals(2, actual.getQuestions().size());
		StudyScreeningQuestion firstStudyScrQuestion = actual.getQuestions().get(0);
		assertEquals(1, firstStudyScrQuestion.getQuestion().getOptions().size());
		assertEquals(0, firstStudyScrQuestion.getOrderNum());
		assertEquals(1, firstStudyScrQuestion.getQuestion().getOptions().size());
		assertEquals(0, firstStudyScrQuestion.getQuestion().getOptions().get(0).getOrderNum());

		StudyScreeningQuestion secondStudyScrQuestion = actual.getQuestions().get(1);
		assertEquals(2, secondStudyScrQuestion.getQuestion().getOptions().size());
		assertEquals(1, secondStudyScrQuestion.getOrderNum());
		assertEquals(2, secondStudyScrQuestion.getQuestion().getOptions().size());
		assertEquals(0, secondStudyScrQuestion.getQuestion().getOptions().get(0).getOrderNum());
		assertEquals(1, secondStudyScrQuestion.getQuestion().getOptions().get(1).getOrderNum());
	}

	@Test
	public void hhh16111_updateQuestionText() {
		String updatedQuestionText = "updatedQuestionText";
		updateQuestionText(qnaire, 0, updatedQuestionText);

		final ScreeningQuestionnaire actual = getQnaire(studyId);

		assertEquals(updatedQuestionText, actual.getQuestions().get(0).getQuestion().getText());
		assertEquals(1, actual.getVersion());
	}

	@Test
	public void hhh16111_addQuestion() {
		String newQuestionText = "new question text";
		addQuestion(qnaire, newQuestionText, "option1","option2");

		final ScreeningQuestionnaire actual = getQnaire(studyId);

		assertEquals(newQuestionText, actual.getQuestions().get(2).getQuestion().getText());
		assertEquals(1, actual.getVersion());
	}

	private ScreeningQuestionnaire getQnaire(long studyId){
		Session s = openSession();
		CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder() ;
		CriteriaQuery<ScreeningQuestionnaire> criteriaQuery = criteriaBuilder.createQuery(ScreeningQuestionnaire.class);
		Root<ScreeningQuestionnaire> screeningQuestionnaireRoot = criteriaQuery.from(ScreeningQuestionnaire.class);
		screeningQuestionnaireRoot.fetch("questions", JoinType.LEFT);
		criteriaQuery.where(criteriaBuilder.equal(screeningQuestionnaireRoot.get("study"), studyId));

		ScreeningQuestionnaire result = s.createQuery(criteriaQuery).getSingleResult();
		s.close();
		return result;
	}

	private void addQuestion(ScreeningQuestionnaire qnaire, String newQuestionText, String ...optionText) {
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		StudyScreeningQuestion studyScreeningQuestion = QuestionBuilder.create(newQuestionText, qnaire).addOptions(optionText).build();
		qnaire.getQuestions().add(studyScreeningQuestion);

		s.update(qnaire);

		tx.commit();
		s.close();
	}

	private void updateQuestionText(ScreeningQuestionnaire qnaire, int questionIndex, String text){
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		qnaire.getQuestions().get(questionIndex).getQuestion().setText(text);
		s.update(qnaire);

		tx.commit();
		s.close();
	}

	@Test
	public void hhh16112_create() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		Study study = s.get(Study.class, studyId);
		SortedSet<StudyEligibilityCriterion> eligibilityCriteria = study.getEligibilityCriteria();

		assertEquals(2, eligibilityCriteria.size());

		tx.commit();
		s.close();
	}

	@Test
	public void hhh16112_remove() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		Study study = s.get(Study.class, studyId);

		study.getEligibilityCriteria().clear();
		s.update(study);

		study = s.get(Study.class, studyId);
		assertEquals(0, study.getEligibilityCriteria().size());

		tx.commit();
		s.close();
	}
}

