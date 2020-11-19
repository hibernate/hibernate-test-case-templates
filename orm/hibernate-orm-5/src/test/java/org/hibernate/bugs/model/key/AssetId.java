package org.hibernate.bugs.model.key;

import java.io.Serializable;

public class AssetId implements Serializable {
    private Long id;
    private Long tenantId;

    public AssetId() {}

    public AssetId(Long id, Long tenantId) {
        this.id = id;
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
