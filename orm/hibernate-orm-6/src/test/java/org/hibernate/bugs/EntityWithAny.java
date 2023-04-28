package org.hibernate.bugs;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.ALL;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;
import org.hibernate.annotations.Cascade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class EntityWithAny {

  @Id
  @GeneratedValue(strategy= IDENTITY)
  private Integer id;

  public String name;
  @Any
  @AnyKeyJavaClass(Integer.class)
  @Column(name = "OBJECTANY_ID")
  @JoinColumn(name = "OBJECTANY_ROLE")
  @Cascade(ALL)
  @AnyDiscriminatorValue(discriminator = "ANY", entity = ObjectAny.class)
  public Object objectAny;

}
