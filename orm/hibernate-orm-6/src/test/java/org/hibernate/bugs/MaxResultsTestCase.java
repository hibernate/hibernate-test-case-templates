/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxResultsTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {Book.class, Author.class};
	}

	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	public List<Author> createEntities() {
		Author author1 = new Author();
		author1.setName("Author1");

		Book book1 = new Book();
		book1.setTitle("Book 1");
		book1.setPages(50);
		author1.addBook( book1 );

		Book book2 = new Book();
		book2.setTitle("Book 2");
		book2.setPages(20);
		author1.addBook( book2 );

		Author author2 = new Author();
		author2.setName("Author2");
		return List.of( author2, author1 );
	}

	@Test
	public void testMaxResultsWithEntityGraph() {
		test( "FROM Author a WHERE a.name LIKE :name ORDER BY a.name" );
	}

	@Test
	public void testWorkingApproach() {
		test( "FROM Author a left join fetch a.books WHERE a.name LIKE :name ORDER BY a.name" );
	}

	private void test(String query) {
		try (Session s = openSession()) {
			s.beginTransaction();
			for ( Author author : createEntities() ) {
				s.persist( author );
			}
			s.getTransaction().commit();
		}
		Author author = null;
		try (Session s = openSession()) {
			s.beginTransaction();
			author = s
					.createSelectionQuery( query, Author.class )
					.setParameter( "name", "Author%" )
					.setMaxResults( 1 )
					.setEntityGraph( createEntityGraph( s ), GraphSemantic.LOAD )
					.getSingleResult();
			s.getTransaction().commit();
		}
		assertThat( author.getBooks() ).hasSize( 2 );
	}

	private EntityGraph<Author> createEntityGraph(Session session) {
		RootGraph<Author> rootGraph = session.createEntityGraph( Author.class );
		rootGraph.addAttributeNode( "books" );
		return rootGraph;
	}

	@Entity(name = "Author")
	public static class Author {

		@Id
		@GeneratedValue
		private Long id;

		private String name;

		@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
		private Set<Book> books = new HashSet<>();

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

		public Set<Book> getBooks() {
			return books;
		}

		public void setBooks(Set<Book> books) {
			this.books = books;
		}

		public void addBook(Book book) {
			books.add( book );
			book.author = this;
		}

		@Override
		public String toString() {
			return id + ":" + name;
		}
	}

	@Entity(name = "Book")
	public static class Book {

		@Id
		@GeneratedValue
		private Long id;

		private String title;

		private int pages;

		@ManyToOne
		private Author author;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getPages() {
			return pages;
		}

		public void setPages(int pages) {
			this.pages = pages;
		}

		public Author getAuthor() {
			return author;
		}

		public void setAuthor(Author author) {
			this.author = author;
		}

		@Override
		public String toString() {
			return id + ":" + title + ":" + pages;
		}
	}
}
