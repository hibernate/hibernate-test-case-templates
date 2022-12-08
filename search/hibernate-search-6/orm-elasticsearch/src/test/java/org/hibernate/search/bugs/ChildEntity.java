package org.hibernate.search.bugs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ChildEntity {

  @Id
  private Long id;

  @OneToOne(mappedBy = "childEntity")
  YourAnnotatedEntity yourAnnotatedEntity;

  protected ChildEntity() {
  }

  public ChildEntity(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

}
