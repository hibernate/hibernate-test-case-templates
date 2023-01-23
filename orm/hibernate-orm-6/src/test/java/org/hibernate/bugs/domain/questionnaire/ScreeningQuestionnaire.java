package org.hibernate.bugs.domain.questionnaire;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.bugs.domain.Study;

import java.util.List;

@Entity
@Table(name = "STUDY_SCREEN_QNAIRE")
public class ScreeningQuestionnaire {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrQnaireSeq")
  @SequenceGenerator(name = "scrQnaireSeq", sequenceName = "STUDY_SCREEN_QNAIRE_SEQ", allocationSize = 1)
  private long id;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaire")
  @OrderColumn(name = "ORDER_NUM")
  private List<StudyScreeningQuestion> questions;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "STUDY_ID")
  private Study study;

  @Version
  @Column(name = "VERSION")
  private long version;

  public ScreeningQuestionnaire() {}

  public ScreeningQuestionnaire(Study study) {
    this.study = study;
  }

  public ScreeningQuestionnaire(Study study, List<StudyScreeningQuestion> questions) {
    this(study);
    this.questions = questions;
  }

  public long getId() {
    return id;
  }

  public List<StudyScreeningQuestion> getQuestions() {
    return questions;
  }

  public void setQuestions(List<StudyScreeningQuestion> questions) {
    this.questions = questions;
  }

  public long getVersion() {
    return version;
  }
}
