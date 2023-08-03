package org.hibernate.bugs;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

  @Id
  @Column(name = "sku")
  private String sku;

  @Column(name = "product_name")
  private String productName;

  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "sku_oid", joinColumns = @JoinColumn(name = "sku"))
  @Column(name = "oid")
  private Set<Integer> productIds = new HashSet<>();

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
        && Objects.equals(productName, offering.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, productName);
  }
}
