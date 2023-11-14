package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Objects;

@Entity
@Cache(region = "main-entity", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MainEntity {
    @Id
    private long id;

    private String mainName;

    private Boolean someFlag;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public Boolean getSomeFlag() {
        return someFlag;
    }

    public void setSomeFlag(Boolean someFlag) {
        this.someFlag = someFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainEntity that = (MainEntity) o;
        return id == that.id && Objects.equals(mainName, that.mainName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainName);
    }
}
