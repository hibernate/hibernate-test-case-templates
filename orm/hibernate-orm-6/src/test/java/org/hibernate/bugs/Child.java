package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.TenantId;

@Entity
public class Child {

  @Id
  @GeneratedValue
  long id;

  @TenantId
  @Column(name = "tenant_id", nullable = false, updatable = false)
  private Long tenantId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_id", nullable = false)
  public Parent parent;

  public long getId() {
    return id;
  }

  public Child setId(long id) {
    this.id = id;
    return this;
  }

  public Long getTenantId() {
    return tenantId;
  }

  public Child setTenantId(Long tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public Parent getParent() {
    return parent;
  }

  public Child setParent(Parent parent) {
    this.parent = parent;
    return this;
  }
}
