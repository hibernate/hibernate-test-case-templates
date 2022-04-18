package org.hibernate.bugs.entity;



import javax.persistence.*;
import java.util.Set;

@Entity
public class Credit {


    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Person person;

    @ElementCollection
    private Set<String> reasons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<String> getReasons() {
        return reasons;
    }

    public void setReasons(Set<String> reasons) {
        this.reasons = reasons;
    }
}
