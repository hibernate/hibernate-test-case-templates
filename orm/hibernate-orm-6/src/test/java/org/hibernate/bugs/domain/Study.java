package org.hibernate.bugs.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.SortNatural;

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

  public Study() {
  }

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
