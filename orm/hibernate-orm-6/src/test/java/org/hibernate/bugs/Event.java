package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "Type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "OrganizerId", referencedColumnName = "Id")
    private Organizer organizer;

    public Event() {
    }

    public Event(Long id, String name, Organizer organizer, Type type) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Type getType() {
        return type;
    }

    public Event setType(Type type) {
        this.type = type;
        return this;
    }
}

enum Type {
    INDOOR, OUTDOOR,
}