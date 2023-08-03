package org.hibernate.bugs;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "offering")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Offering implements Serializable {

  /**
   * Unique identifier for the Offering.
   *
   * <p>Because we want only a single instance per SKU, we'll use the SKU as the only primary key.
   *
   * <p>Note that many types of SKUs exist within Red Hat, Offering will be Marketing SKUs only.
   */
  @Id
  @Column(name = "sku")
  private String sku;

  /**
   * Customer-facing name for the offering.
   *
   * <p>E.g. Red Hat Enterprise Linux Server
   */
  @Column(name = "product_name")
  private String productName;

  /**
   * Customer-facing description for the offering.
   *
   * <p>E.g. Red Hat Enterprise Linux Server with Smart Management + Satellite, Standard (Physical
   * or Virtual Nodes)
   */
  @Column(name = "description")
  private String description;

  /**
   * Category for the offering.
   *
   * <p>E.g. "Red Hat Enterprise Linux" or "Ansible"
   */
  @Column(name = "product_family")
  private String productFamily;

  /** Internal identifiers for products that compose an Offering. */
  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "sku_child_sku", joinColumns = @JoinColumn(name = "sku"))
  @Column(name = "child_sku")
  private Set<String> childSkus = new HashSet<>();

  /**
   * Numeric identifiers for Engineering Products provided by the offering.
   *
   * <p>Engineering products define a set of installable content.
   *
   * <p>See
   * https://www.candlepinproject.org/docs/candlepin/how_subscriptions_work.html#engineering-products
   *
   * <p>Sometimes referred to as "provided products".
   */
  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "sku_oid", joinColumns = @JoinColumn(name = "sku"))
  @Column(name = "oid")
  private Set<Integer> productIds = new HashSet<>();

  /** Effective standard CPU cores capacity per quantity of subscription to this offering. */
  @Column(name = "cores")
  private Integer cores;

  /** Effective standard CPU sockets capacity per quantity of subscription to this offering. */
  @Column(name = "sockets")
  private Integer sockets;

  /** Effective hypervisor CPU cores capacity per quantity of subscription to this offering. */
  @Column(name = "hypervisor_cores")
  private Integer hypervisorCores;

  /** Effective hypervisor CPU sockets capacity per quantity of subscription to this offering. */
  @Column(name = "hypervisor_sockets")
  private Integer hypervisorSockets;

  /** Syspurpose Role for the offering */
  @Column(name = "role")
  private String role;

//  @Column(name = "sla")
//  @Convert(converter = ServiceLevel.EnumConverter.class)
//  private ServiceLevel serviceLevel;
//
//  /** Syspurpose Usage for the offering */
//  @Column(name = "usage")
//  @Convert(converter = Usage.EnumConverter.class)
//  private Usage usage;

  // Lombok would name the getter "isHasUnlimitedGuestSockets"
  @Getter(AccessLevel.NONE)
  @Column(name = "has_unlimited_usage")
  private Boolean hasUnlimitedUsage;

  // Derived SKU, needed to track necessary updates when a derived SKU is changed
  @Column(name = "derived_sku")
  private String derivedSku;

  public Boolean getHasUnlimitedUsage() {
    return hasUnlimitedUsage;
  }

  public List<String> getProductIdsAsStrings() {
    return getProductIds().stream().map(String::valueOf).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Offering)) {
      return false;
    }
    Offering offering = (Offering) o;
    return Objects.equals(sku, offering.sku)
        && Objects.equals(productName, offering.productName)
        && Objects.equals(description, offering.description)
        && Objects.equals(productFamily, offering.productFamily)
        && Objects.equals(cores, offering.cores)
        && Objects.equals(sockets, offering.sockets)
        && Objects.equals(hypervisorCores, offering.hypervisorCores)
        && Objects.equals(hypervisorSockets, offering.hypervisorSockets)
        && Objects.equals(role, offering.role)
//        && serviceLevel == offering.serviceLevel
//        && usage == offering.usage
        && Objects.equals(hasUnlimitedUsage, offering.hasUnlimitedUsage)
        && Objects.equals(derivedSku, offering.derivedSku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        sku,
        productName,
        description,
        productFamily,
        cores,
        sockets,
        hypervisorCores,
        hypervisorSockets,
        role,
//        serviceLevel,
//        usage,
        hasUnlimitedUsage,
        derivedSku);
  }
}
