package org.hibernate.bugs.HHH12436;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Primari entity, owns optionalData
 *
 * @author localEvg
 */
@Entity
public class Prima implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "parent", optional = true , cascade = CascadeType.ALL)
    private Secunda optionalData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Secunda getOptionalData() {
        return optionalData;
    }

    public void setOptionalData(Secunda optionalData) {
        this.optionalData = optionalData;
    }

}
