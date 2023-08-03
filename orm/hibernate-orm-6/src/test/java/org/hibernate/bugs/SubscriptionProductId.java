package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.bugs.Subscription.SubscriptionCompoundId;
import org.hibernate.bugs.SubscriptionProductId.SubscriptionProductIdKey;

/** Capacity provided by a subscription for a given product. */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@IdClass(SubscriptionProductIdKey.class)
@Table(name = "subscription_product_ids")
public class SubscriptionProductId implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
  @JoinColumn(name = "start_date", referencedColumnName = "start_date")
  private Subscription subscription;

  @Id
  @Column(name = "product_id")
  private String productId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SubscriptionProductId)) {
      return false;
    }
    SubscriptionProductId other = (SubscriptionProductId) o;

    return Objects.equals(this.productId, other.getProductId())
        && Objects.equals(
        subscription.getSubscriptionId(), other.getSubscription().getSubscriptionId())
        && Objects.equals(subscription.getStartDate(), other.getSubscription().getStartDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, subscription.getSubscriptionId(), subscription.getStartDate());
  }

  /** Primary key for a subscription_product_ids record */
  @Getter
  @Setter
  public static class SubscriptionProductIdKey implements Serializable {
    // NB: this name must match the field name used in the dependent entity (SubscriptionProductId)
    // See JPA 2.1 specification, section 2.4.1.3 example 2a
    private SubscriptionCompoundId subscription;

    private String productId;

    public SubscriptionProductIdKey() {}

    public SubscriptionProductIdKey(SubscriptionCompoundId subscription, String productId) {
      this.subscription = subscription;
      this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof SubscriptionProductIdKey)) {
        return false;
      }
      SubscriptionProductIdKey productIdKey = (SubscriptionProductIdKey) o;

      return Objects.equals(productId, productIdKey.getProductId())
          && Objects.equals(
          subscription.getSubscriptionId(), productIdKey.getSubscription().getSubscriptionId())
          && Objects.equals(
          subscription.getStartDate(), productIdKey.getSubscription().getStartDate());
    }

    @Override
    public int hashCode() {
      return Objects.hash(productId, subscription.getSubscriptionId(), subscription.getStartDate());
    }
  }
}
