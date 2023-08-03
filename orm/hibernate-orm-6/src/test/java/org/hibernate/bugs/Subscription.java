package org.hibernate.bugs;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@IdClass(Subscription.SubscriptionCompoundId.class)
@Table(name = "subscription")
// The below graph fetches all associations needed during subscription sync to avoid n+1 queries
@NamedEntityGraph(
    name = "graph.SubscriptionSync",
    attributeNodes = {
        @NamedAttributeNode(value = "offering", subgraph = "subgraph.offering"),
        @NamedAttributeNode("subscriptionMeasurements"),
        @NamedAttributeNode("subscriptionProductIds")
    },
    subgraphs = {
        @NamedSubgraph(name = "subgraph.offering", attributeNodes = @NamedAttributeNode("productIds"))
    })
public class Subscription implements Serializable {

  @Id
  @Column(name = "subscription_id")
  private String subscriptionId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sku")
  private Offering offering;

  @Column(name = "org_id")
  private String orgId;

  @Id
  @Column(name = "start_date")
  private OffsetDateTime startDate;

  @Column(name = "end_date")
  private OffsetDateTime endDate;

  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  @ToString.Exclude
  private Set<SubscriptionProductId> subscriptionProductIds = new HashSet<>();

  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  @ToString.Exclude // Excluded to prevent fetching a lazy-loaded collection
  private Set<SubscriptionMeasurement> subscriptionMeasurements = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Subscription)) {
      return false;
    }
    Subscription sub = (Subscription) o;

    return Objects.equals(subscriptionId, sub.getSubscriptionId())
        && Objects.equals(orgId, sub.getOrgId())
        && Objects.equals(startDate, sub.getStartDate())
        && Objects.equals(endDate, sub.getEndDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        subscriptionId,
        orgId,
        startDate,
        endDate);
  }

  /** Composite ID class for Subscription entities. */
  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode
  public static class SubscriptionCompoundId implements Serializable {
    private String subscriptionId;
    private OffsetDateTime startDate;

    public SubscriptionCompoundId(String subscriptionId, OffsetDateTime startDate) {
      this.subscriptionId = subscriptionId;
      this.startDate = startDate;
    }

    public SubscriptionCompoundId() {
      // default
    }
  }

  public void addSubscriptionProductId(SubscriptionProductId spi) {
    spi.setSubscription(this);
    subscriptionProductIds.add(spi);
  }

  public void addSubscriptionMeasurement(SubscriptionMeasurement sm) {
    sm.setSubscription(this);
    subscriptionMeasurements.add(sm);
  }

  public void addSubscriptionMeasurements(Collection<SubscriptionMeasurement> measurements) {
    for (SubscriptionMeasurement measurement : measurements) {
      measurement.setSubscription(this);
      subscriptionMeasurements.add(measurement);
    }
  }
}
