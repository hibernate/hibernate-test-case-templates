package org.hibernate.bugs.domain.questionnaire;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "STUDY_SCREENING_QUESTION")
public class StudyScreeningQuestion {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrQuestionSeq")
  @SequenceGenerator(name = "scrQuestionSeq", sequenceName = "STUDY_SCREENING_QUESTION_SEQ", allocationSize = 1)
  private long id;
  @Column(name = "ORDER_NUM")
  private int orderNum;
  @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
  @Cascade({CascadeType.MERGE, CascadeType.LOCK})
  @JoinColumn(name = "SCREENING_QUESTION_ID")
  private ScreeningQuestion question;

  @ManyToOne
  @JoinColumn(name = "QUESTIONNAIRE_ID")
  private ScreeningQuestionnaire questionnaire;

  public StudyScreeningQuestion() {}

  public StudyScreeningQuestion(ScreeningQuestion question) {
    this.question = question;
  }

  public long getId() {
    return id;
  }

  public int getOrderNum() {
    return this.orderNum;
  }

  public void setOrderNum(int orderNum) {
    this.orderNum = orderNum;
  }

  public ScreeningQuestionnaire getQuestionnaire() {
    return questionnaire;
  }

  public void setQuestionnaire(ScreeningQuestionnaire questionnaire) {
    this.questionnaire = questionnaire;
  }

  public void setQuestion(ScreeningQuestion question) {
    this.question = question;
  }

  public ScreeningQuestion getQuestion() {
    return question;
  }
}
