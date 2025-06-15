package org.hibernate.bugs.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Entity
@Table(name = "INSTRUMENT")
@SecondaryTable(name = "INSTRUMENT_CREDIT_DERIVATIVE", pkJoinColumns = @PrimaryKeyJoinColumn(name = "INSTRUMENT_ID", referencedColumnName = "ID"))
public abstract class Instrument {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private InstrumentCode instrumentCode;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "DESCRIPTION")
    private String description;

    private CreditDerivative creditDerivative;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "INSTRUMENT_LINE", joinColumns = @JoinColumn(name = "INSTRUMENT_ID", referencedColumnName = "ID"))
    private List<InstrumentLine> instrumentLines;

}
