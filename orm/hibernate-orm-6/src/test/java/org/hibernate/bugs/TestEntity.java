package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TEST_ENTITY")
public class TestEntity implements Serializable {
  private static final long serialVersionUID = 3200005004981651401L;
  @Id
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(name = "ARCHIVED_DATE")
  private LocalDateTime archivedDate;
  @Column(name = "PUBLISHABLE")
  private boolean publishable;
  @Column(name = "POSTING_ACTIVATION_DATE")
  private LocalDateTime postingActivationDate;
  @Column(name = "POSTING_DEACTIVATION_DATE")
  private LocalDateTime postingDeactivationDate;

  public TestEntity() {}

  public TestEntity(long id) {
    this.id=id;
  }

  public LocalDateTime getArchivedDate() {
    return this.archivedDate;
  }

  public Long getId() {
    return id;
  }

  public boolean isPublishable() {
    return publishable;
  }

  public void setPublishable(boolean publishable) {
    this.publishable = publishable;
  }

  public LocalDateTime getPostingActivationDate() {
    return postingActivationDate;
  }

  public void setPostingActivationDate(LocalDateTime postingActivationDate) {
    this.postingActivationDate = postingActivationDate;
  }

  public LocalDateTime getPostingDeactivationDate() {
    return postingDeactivationDate;
  }

  public void setPostingDeactivationDate(LocalDateTime postingDeactivationDate) {
    this.postingDeactivationDate = postingDeactivationDate;
  }
}
