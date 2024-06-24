package org.hibernate.bugs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcType;
import org.hibernate.bugs.enums.Status;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity(name = "sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Status status;

    public Sample() {
    }


    public Sample(final Status status) {
        this.status = status;
    }

}
