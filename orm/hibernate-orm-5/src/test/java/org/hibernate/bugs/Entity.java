package org.hibernate.bugs;

import java.sql.Date;

import javax.persistence.Convert;
import javax.persistence.Id;

import org.joda.time.LocalDate;

@javax.persistence.Entity
public class Entity {

    @Id
    private Long id;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate localDate;

    private java.sql.Date sqlDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }
}
