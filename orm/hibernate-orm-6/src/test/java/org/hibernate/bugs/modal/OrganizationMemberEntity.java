package org.hibernate.bugs.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "\"organization_member\"")
@SoftDelete
public class OrganizationMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "primary_")
    private Boolean primary;

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}