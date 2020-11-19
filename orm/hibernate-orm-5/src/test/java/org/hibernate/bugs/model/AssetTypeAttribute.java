package org.hibernate.bugs.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "asset_type_attribute")
public class AssetTypeAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetTypeAttribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssetAssetTypeAttribute> assetAssetTypeAttributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
