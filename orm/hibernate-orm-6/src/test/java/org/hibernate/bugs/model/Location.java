package org.hibernate.bugs.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Point point;

    public Location() {
    }

    public Location(Long id, Point point) {
        this.id = id;
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", point=" + point +
                '}';
    }
}
