package org.hibernate.bugs.domain.questionnaire;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SCREENING_QUESTION")
public class ScreeningQuestion implements Serializable {
  private static final long serialVersionUID = 6179875744333429399L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrQuesSeq")
  @SequenceGenerator(name = "scrQuesSeq", sequenceName = "SCREENING_QUESTION_SEQ", allocationSize = 1)
  private long id;
  @Column(name = "TEXT")
  private String text;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question", orphanRemoval = true)
  @OrderColumn(name = "ORDER_NUM")
  private List<ScreeningQuestionOption> options;
  public ScreeningQuestion() {}
  public ScreeningQuestion(String text) {
    this.text = text;
  }
  public long getId() {
    return id;
  }
  public String getText() {
    return text;
  }

  public void setText(String text) { this.text=text; }
  public List<ScreeningQuestionOption> getOptions() {
    return options;
  }
  public void setOptions(List<ScreeningQuestionOption> options) {
    this.options = options;
  }
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("[" + this.getId() + "] ");
    str.append("text: ").append(this.getText()).append(", ");
    str.append("answers: ");

    if (this.getOptions() != null) {
      for (ScreeningQuestionOption option : getOptions()) {
        str.append("\n").append(option.toString());
      }
    }

    return str.toString();
  }
}
