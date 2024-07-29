package org.hibernate.bugs;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Book")
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="ISBN")
	private String isbn;
	
	@OneToMany(mappedBy = "book", orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BookNote> bookNotes = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<BookNote> getBookNotes() {
		return bookNotes;
	}

	public void setBookNotes(Set<BookNote> bookNotes) {
		this.bookNotes = bookNotes;
	}
}
