package org.hibernate.bugs;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAR")
public class Car implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    private String name;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "MAIN_CAR", columnDefinition = "NUMBER(38,0)")
    private Boolean mainCar;

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

    public Boolean getMainCar() {
        return mainCar;
    }

    public void setMainCar(Boolean mainCar) {
        this.mainCar = mainCar;
    }
}
