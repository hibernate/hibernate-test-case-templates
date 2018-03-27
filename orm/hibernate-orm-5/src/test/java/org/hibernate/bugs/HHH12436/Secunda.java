package org.hibernate.bugs.HHH12436;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

/**
 * Secondary entity, owns by Prima, shared primary key
 *
 * @author localEvg
 */
@Entity
public class Secunda implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Prima parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prima getParent() {
        return parent;
    }

    public void setParent(Prima parent) {
        this.parent = parent;
    }

}
