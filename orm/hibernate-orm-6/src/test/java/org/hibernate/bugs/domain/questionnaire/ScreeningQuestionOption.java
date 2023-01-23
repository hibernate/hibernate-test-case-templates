package org.hibernate.bugs.domain.questionnaire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
@Entity
@Table(name = "STUDY_SCR_QUES_OPTION")
public class ScreeningQuestionOption implements Serializable {
  private static final long serialVersionUID = 6164375984333429399L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studyScrQuesOptSeq")
  @SequenceGenerator(name = "studyScrQuesOptSeq", sequenceName = "STUDY_SCR_QUES_OPTION_SEQ", allocationSize = 1)
  private long id;
  @Column(name = "ORDER_NUM")
  private int orderNum;
  @Column(name = "TEXT")
  private String text;
  @ManyToOne
  @JoinColumn(name = "SCREENING_QUESTION_ID")
  private ScreeningQuestion question;
  public ScreeningQuestionOption() {}
  public ScreeningQuestionOption(String text) {
    this.text = text;
  }
  public ScreeningQuestionOption(String text, ScreeningQuestion question) {
    this(text);
    this.question = question;
  }
  public long getId() {
    return id;
  }

  public int getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(int orderNum) {
    this.orderNum = orderNum;
  }
  public String getText() {
    return text;
  }

  public ScreeningQuestion getQuestion() {
    return question;
  }
}
