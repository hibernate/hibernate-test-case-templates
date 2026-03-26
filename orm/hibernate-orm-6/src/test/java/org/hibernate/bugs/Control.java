package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "control")
public class Control implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "control_domain_id")
    private ControlDomain controlDomain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControlDomain getControlDomain() {
        return controlDomain;
    }

    public void setControlDomain(ControlDomain controlDomain) {
        this.controlDomain = controlDomain;
    }
}