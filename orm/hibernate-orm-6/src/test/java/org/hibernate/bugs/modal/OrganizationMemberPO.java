package org.hibernate.bugs.modal;


public class OrganizationMemberPO {

    private Long id;

    private Long userId;

    private Long organizationId;

    private String name;

    private String displayPath;

    private Boolean primary;

    public OrganizationMemberPO(Long id, Long userId, Long organizationId, String name, String displayPath, Boolean primary) {
        this.id = id;
        this.userId = userId;
        this.organizationId = organizationId;
        this.name = name;
        this.displayPath = displayPath;
        this.primary = primary;
    }

    public OrganizationMemberPO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
