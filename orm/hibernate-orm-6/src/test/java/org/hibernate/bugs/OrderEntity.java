package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Persister;
import org.hibernate.persister.entity.JoinedSubclassEntityPersister;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Persister(impl = JoinedSubclassEntityPersister.class)
public class OrderEntity {

    @Id
    Long id;

    @OneToMany(
            mappedBy = "order",
            targetEntity = OrderItem.class,
            cascade = {CascadeType.ALL}
    )
    List<OrderItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
