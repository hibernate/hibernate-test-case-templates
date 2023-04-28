package org.hibernate.bugs;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ObjectAny {

  @Id
  @GeneratedValue(strategy= IDENTITY)
  private Integer id;
}
