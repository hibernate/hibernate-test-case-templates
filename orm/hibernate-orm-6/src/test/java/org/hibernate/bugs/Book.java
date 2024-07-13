package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Author mainAuthor;

    @ManyToOne
    private Author contributedAuthor;

    public Author getMainAuthor() {
        return mainAuthor;
    }

    public Author getContributedAuthor() {
        return contributedAuthor;
    }

    public Book setMainAuthor(Author value) {
        mainAuthor = value;
        return this;
    }

    public Book setContributedAuthor(Author value) {
        contributedAuthor = value;
        return this;
    }
}
