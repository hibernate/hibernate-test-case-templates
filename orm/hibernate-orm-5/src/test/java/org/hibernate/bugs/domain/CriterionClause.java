package org.hibernate.bugs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
