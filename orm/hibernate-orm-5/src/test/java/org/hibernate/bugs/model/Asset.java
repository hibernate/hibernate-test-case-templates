package org.hibernate.bugs.model;

import org.hibernate.bugs.model.key.AssetId;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "asset")
@IdClass(AssetId.class)
public class Asset {
    @Id
    private Long id;

    @Id
    @Column(name = "tenant_id")
    private Long tenantId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssetAssetTypeAttribute> assetAssetTypeAttributes;

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<AssetAssetTypeAttribute> getAssetAssetTypeAttributes() {
        return assetAssetTypeAttributes;
    }

    public void setAssetAssetTypeAttributes(Set<AssetAssetTypeAttribute> assetAssetTypeAttributes) {
        this.assetAssetTypeAttributes = assetAssetTypeAttributes;
    }
}
