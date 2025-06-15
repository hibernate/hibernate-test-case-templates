package org.hibernate.bugs.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "INSTRUMENT")
@SecondaryTable(name = "INSTRUMENT_CREDIT_DERIVATIVE", pkJoinColumns = @PrimaryKeyJoinColumn(name = "INSTRUMENT_ID", referencedColumnName = "ID"))
public abstract class Instrument {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CreditDerivative creditDerivative;

}
