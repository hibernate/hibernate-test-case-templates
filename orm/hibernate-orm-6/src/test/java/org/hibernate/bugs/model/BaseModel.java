package org.hibernate.bugs.model;


import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
@IdClass(BaseModelId.class)
public class BaseModel {
  @Id
  private String accountName;

  @Id
  private String accountType;

  private Long credit;
}

