package org.hibernate.bugs.domain.questionnaire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import static jakarta.persistence.CascadeType.ALL;

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
