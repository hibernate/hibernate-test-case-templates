package org.hibernate.bugs.model.key;

import java.io.Serializable;

public class AssetAttributeId implements Serializable {
    private Long assetId;
    private Long assetTypeAttributeId;
    private Long tenantId;

    public AssetAttributeId() {}

    public AssetAttributeId(Long assetId, Long assetTypeAttributeId, Long tenantId) {
        this.assetId = assetId;
        this.assetTypeAttributeId = assetTypeAttributeId;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
