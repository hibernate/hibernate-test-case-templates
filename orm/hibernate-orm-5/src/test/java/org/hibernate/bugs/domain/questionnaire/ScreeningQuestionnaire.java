package org.hibernate.bugs.domain.questionnaire;

import org.hibernate.bugs.domain.Study;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
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
