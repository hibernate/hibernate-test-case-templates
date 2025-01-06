package org.hibernate.bugs.model;

import java.util.List;

public class Point {

    private String type;
    private List<Double> coordinates;

    public Point() {
        type = "Point";
    }

    public Point(List<Double> coordinates) {
        type = "Point";
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }
}
