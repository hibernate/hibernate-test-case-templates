package org.hibernate.bugs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "CRITERION_CLAUSE")
public class CriterionClause implements Serializable {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criterionClauseIdSequence")
  @SequenceGenerator(name = "criterionClauseIdSequence", sequenceName = "CRITERION_CLAUSE_SEQ", allocationSize = 1)
  private long id;
  @Column(name = "NAME")
  private String name;

  @Column(name = "ORDER_NUM")
  private int orderNum;

  public CriterionClause(String name, int orderNum) {
    this.name =name;
    this.orderNum =orderNum;
  }

  public CriterionClause() {
  }
}
