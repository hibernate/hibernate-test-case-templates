package org.hibernate.bugs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcType;
import org.hibernate.bugs.enums.ComposeStatus;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity(name = "compose_sample")
public class ComposeSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "compose_status", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ComposeStatus composeStatus;

    public ComposeSample() {
    }


    public ComposeSample(final ComposeStatus status) {
        this.composeStatus = status;
    }

}
