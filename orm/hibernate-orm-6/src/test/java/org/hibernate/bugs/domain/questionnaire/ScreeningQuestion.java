package org.hibernate.bugs.domain.questionnaire;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
