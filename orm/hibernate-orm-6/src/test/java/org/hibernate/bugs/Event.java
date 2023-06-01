package org.hibernate.bugs;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

	@Enumerated(EnumType.STRING)
    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

}