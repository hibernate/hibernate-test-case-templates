package org.hibernate.bugs;

import java.sql.Date;

import javax.persistence.Convert;
import javax.persistence.Id;

@javax.persistence.Entity
public class Entity {

    @Id
    private Long id;

    @Convert(converter = LocalDateConverter.class)
    private String localDate;

    private java.sql.Date sqlDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }
}
