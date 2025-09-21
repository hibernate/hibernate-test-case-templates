package org.hibernate.bugs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  private long id;

  @Column(name = "attributes", nullable = false, columnDefinition = "text[]")
  private String[] attributes;

}
