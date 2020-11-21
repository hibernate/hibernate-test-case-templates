package org.hibernate.bugs.model;

import org.hibernate.bugs.model.key.AssetAttributeId;

import javax.persistence.*;

@Entity
@Table(name = "asset_asset_type_attribute")
@IdClass(AssetAttributeId.class)
public class AssetAssetTypeAttribute {

    @Id
    @Column(name = "tenant_id", insertable = false, updatable = false)
    private Long tenantId;

    @Id
    @Column(name = "asset_id", insertable = false, updatable = false)
    private Long assetId;

    @Id
    @Column(name = "asset_type_attribute_id")
    private Long assetTypeAttributeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "asset_id", referencedColumnName = "id"),
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id"),
    })
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "asset_type_attribute_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AssetTypeAttribute assetTypeAttribute;

    private String value;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getAssetTypeAttributeId() {
        return assetTypeAttributeId;
    }

    public void setAssetTypeAttributeId(Long assetTypeAttributeId) {
        this.assetTypeAttributeId = assetTypeAttributeId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetTypeAttribute getAssetTypeAttribute() {
        return assetTypeAttribute;
    }

    public void setAssetTypeAttribute(AssetTypeAttribute assetTypeAttribute) {
        this.assetTypeAttribute = assetTypeAttribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
