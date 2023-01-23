package org.hibernate.bugs.domain.questionnaire;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
