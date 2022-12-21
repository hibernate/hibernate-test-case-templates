package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "children")
@IdClass(Child.IdClass.class)
public class Child
{
    @Id
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    public static class IdClass implements Serializable
    {
        private long id;

        private Parent parent;
    }
}
