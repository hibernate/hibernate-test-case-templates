package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.bugs.SubscriptionMeasurement.SubscriptionMeasurementKey;

/** Capacity provided by a subscription for a given product. */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "subscription_measurements")
@IdClass(SubscriptionMeasurementKey.class)
public class SubscriptionMeasurement implements Serializable {
  @Id
  @ManyToOne
  @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
  @JoinColumn(name = "start_date", referencedColumnName = "start_date")
  private Subscription subscription;

  @Id
  @Column(name = "metric_id")
  private String metricId;

  @Id
  @Column(name = "measurement_type")
  private String measurementType;

  @Column(name = "measurement_value")
  private Double value;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SubscriptionMeasurement)) {
      return false;
    }
    SubscriptionMeasurement measurement = (SubscriptionMeasurement) o;

    return Objects.equals(metricId, measurement.getMetricId())
        && Objects.equals(measurementType, measurement.getMeasurementType())
        && Objects.equals(
        subscription.getSubscriptionId(), measurement.getSubscription().getSubscriptionId())
        && Objects.equals(
        subscription.getStartDate(), measurement.getSubscription().getStartDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        metricId, measurementType, subscription.getSubscriptionId(), subscription.getStartDate());
  }

  /** Primary key for a subscription_measurement record */
  @Getter
  @Setter
  public static class SubscriptionMeasurementKey implements Serializable {
    // NB: this name must match the field name used in the dependent entity
    // (SubscriptionMeasurement) See JPA 2.1 specification, section 2.4.1.3 example 2a
    private SubscriptionCompoundId subscription;

    private String metricId;

    private String measurementType;

    public SubscriptionMeasurementKey() {}

    public SubscriptionMeasurementKey(
        SubscriptionCompoundId subscription, String metricId, String measurementType) {
      this.subscription = subscription;
      this.metricId = metricId;
      this.measurementType = measurementType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof SubscriptionMeasurementKey)) {
        return false;
      }
      SubscriptionMeasurementKey measurementKey = (SubscriptionMeasurementKey) o;

      return Objects.equals(metricId, measurementKey.getMetricId())
          && Objects.equals(measurementType, measurementKey.getMeasurementType())
          && Objects.equals(
          subscription.getSubscriptionId(),
          measurementKey.getSubscription().getSubscriptionId())
          && Objects.equals(
          subscription.getStartDate(), measurementKey.getSubscription().getStartDate());
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          metricId, measurementType, subscription.getSubscriptionId(), subscription.getStartDate());
    }
  }
}
