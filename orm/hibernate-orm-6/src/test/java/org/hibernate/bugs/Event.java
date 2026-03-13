package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP )
    private Date createdOn;


//    @Formula("(select n.info from notification_info n where n.event_id = id)")  // Normal sentence
    @Formula("(select n.\"info\" from \"notification_info\" n where n.\"event_id\" = \"id\")")  // Add quotation marks
    private String nInfo;

    public Event() {
    }

    public Event(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", nInfo='" + nInfo + '\'' +
                '}';
    }
}
