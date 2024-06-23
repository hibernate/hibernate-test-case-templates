package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Item {

  @Id
  @GeneratedValue
  private long id;

  public Item setId(long id) {
    this.id = id;
    return this;
  }
}
