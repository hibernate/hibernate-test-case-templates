package org.hibernate.bugs.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STUDY_ELIGIBILITY_CRITERION")
public class StudyEligibilityCriterion  implements Serializable, Comparable<StudyEligibilityCriterion> {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criterionIdSequence")
  @SequenceGenerator(name = "criterionIdSequence", sequenceName = "CRITERION_SEQ", allocationSize = 1)
  private long id;

  @Column(name = "NAME")
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "STUDY_ID")
  private Study study;

  @Column(name = "ORDER_NUM")
  private int orderNum;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "CRITERION_ID")
  @OrderColumn(name = "ORDER_NUM")
  private List<CriterionClause> clauses;

  public StudyEligibilityCriterion() {
  }

  public StudyEligibilityCriterion(String name) {
    this.name = name;
  }

  public StudyEligibilityCriterion(int orderNum, String name, Study study) {
    this.orderNum = orderNum;
    this.name = name;
    this.study = study;
  }
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Study getStudy() {
    return study;
  }

  public void setStudy(Study study) {
    this.study = study;
  }

  public int getOrderNum() {
    return orderNum;
  }

  public List<CriterionClause> getClauses() {
    return clauses;
  }

  public void setClauses(List<CriterionClause> clauses) {
    this.clauses = clauses;
  }


  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, study, orderNum);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass() || !super.equals(o)) {
      return false;
    }

    StudyEligibilityCriterion that = (StudyEligibilityCriterion) o;

    if (this.getOrderNum() != that.getOrderNum()) {
      return false;
    }
    return Objects.equals(name, that.name) && Objects.equals(study, that.study);
  }

  @Override
  public String toString() {
    String str = name;
    if (study != null) {
      str += "Study id: " + study.getId();
    } else {
      str += "Study id: " + null;
    }
    str += super.toString();
    return str;
  }

  @Override
  public int compareTo(StudyEligibilityCriterion other) {
    if (this.orderNum == other.orderNum && this.equals(other)) {
      return 0;
    }

    if (this.orderNum > other.orderNum) {
      return 1;
    }

    return -1;
  }

  public long getId() {
    return id;
  }
}
