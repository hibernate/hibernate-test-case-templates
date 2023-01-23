package org.hibernate.bugs.domain;

import org.hibernate.annotations.SortNatural;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "STUDY")
public class Study implements Serializable {
  private static final long serialVersionUID = 9058013917689430237L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studySeq")
  @SequenceGenerator(name = "studySeq", sequenceName = "STUDY_SEQ", allocationSize = 1)
  private long id;

  @SortNatural
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "study", orphanRemoval = true)
  private SortedSet<StudyEligibilityCriterion> eligibilityCriteria;

  public Study() {}

  public Study(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }


  public SortedSet<StudyEligibilityCriterion> getEligibilityCriteria() {
    return eligibilityCriteria;
  }

  public void setEligibilityCriteria(Set<StudyEligibilityCriterion> eligibilityCriteria) {
    if (eligibilityCriteria != null) {
      if (this.eligibilityCriteria == null) {
        this.eligibilityCriteria = new TreeSet<>();
      }
      this.eligibilityCriteria.addAll(eligibilityCriteria);
    } else {
      this.eligibilityCriteria.clear();
    }
  }
}
