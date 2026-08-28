package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_info")
public class NotificationInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "info")
    private String info;

    public NotificationInfo() {}

    public NotificationInfo(Long eventId, String info) {
        this.eventId = eventId;
        this.info = info;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NotificationInfo{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", info='" + info + '\'' +
                '}';
    }
}
