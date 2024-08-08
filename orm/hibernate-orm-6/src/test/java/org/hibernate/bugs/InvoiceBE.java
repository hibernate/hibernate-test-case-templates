package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "invoice")
public class InvoiceBE {

  @Id
  @GeneratedValue
  private long id;

  @Column(name = "removed", nullable = false)
  private boolean removed;

  public InvoiceBE setId(long id) {
    this.id = id;
    return this;
  }

  public InvoiceBE setRemoved(boolean removed) {
    this.removed = removed;
    return this;
  }
}
