package org.spring.data.jpa.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class OurEntity {

    @Id
    @SequenceGenerator(name = "ourEntitySeq", sequenceName = "OUR_ENTITY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ourEntitySeq")
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "common_name")
    private String commonName;
    @Column(name = "our_status")
    private OurStatus ourStatus;

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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String common_name) {
        this.commonName = common_name;
    }

    public OurStatus getOurStatus() {
        return ourStatus;
    }

    public void setOurStatus(OurStatus ourStatus) {
        this.ourStatus = ourStatus;
    }
}
