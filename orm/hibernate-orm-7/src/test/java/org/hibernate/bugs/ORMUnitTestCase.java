package org.hibernate.bugs;

import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.cfg.AvailableSettings;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Tuple;
import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
@DomainModel(
		annotatedClasses = {
				ORMUnitTestCase.Book.class,
				ORMUnitTestCase.SpellBook.class,
				ORMUnitTestCase.Novel.class
		}
)
@ServiceRegistry(settings = {
		@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
		@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),
})
@SessionFactory
class ORMUnitTestCase {

	@Test
	void softDeleteSelectQuery(SessionFactoryScope scope) throws Exception {
		SpellBook necronomicon = new SpellBook( 1, "Necronomicon", true );
		SpellBook bookOfShadows = new SpellBook( 2, "Book of Shadows", false );
		Novel theHobbit = new Novel( 3, "The Hobbit", "Fantasy" );
		scope.inTransaction( session -> {
			session.persist( bookOfShadows );
			session.persist( necronomicon );
			session.persist( theHobbit );
		} );
		scope.inTransaction( session -> session
				.createMutationQuery( "delete from SpellBook where id = :id" )
				.setParameter( "id", necronomicon.getId() )
				.executeUpdate()
		);
		// Check the actual content of the database
		scope.inTransaction( session -> {
			List<Tuple> tuples = session
					.createNativeQuery( "select title, deleted from " + SpellBook.TABLE_NAME + " order by id", Tuple.class )
					.getResultList();
			assertThat( tuples )
					.extracting( tuple -> tuple.get( "title" ) )
					.containsExactly( necronomicon.getTitle(), bookOfShadows.getTitle() );
			assertThat( tuples )
					.extracting( tuple -> tuple.get( "deleted" ) )
					.containsExactly( true, false );
		});
		// Select with HQL the parent entity
		scope.inTransaction( session -> {
			List<Book> results = session
					.createSelectionQuery( "from Book", Book.class )
					.getResultList();
			assertThat( results )
					.extracting( Book::getTitle )
					.containsExactlyInAnyOrder( bookOfShadows.getTitle(), theHobbit.getTitle() );
		});
		// Select with HQL the exact table
		scope.inTransaction( session -> {
			List<SpellBook> results = session
					.createSelectionQuery( "from SpellBook ", SpellBook.class )
					.getResultList();
			assertThat( results )
					.extracting( Book::getTitle )
					.containsExactlyInAnyOrder( bookOfShadows.getTitle() );
		});
	}

	@Entity(name = "Book")
	@Table(name = Book.TABLE_NAME)
	@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
	@SoftDelete
	public static class Book {
		private static final String TABLE_NAME = "ORMTest_BOOK";

		@Id
		private Integer id;
		private String title;

		public Book() {
		}

		public Book(Integer id, String title) {
			this.id = id;
			this.title = title;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			Book book = (Book) o;
			return Objects.equals( id, book.id ) && Objects.equals( title, book.title );
		}

		@Override
		public int hashCode() {
			return Objects.hash( id, title );
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "{id=" + id + ", title='" + title + "'}";
		}
	}

	@Entity(name = "SpellBook")
	@Table(name = SpellBook.TABLE_NAME)
	@SoftDelete
	public static class SpellBook extends Book {
		private static final String TABLE_NAME = "ORMTest_SPELL_BOOK";

		private boolean forbidden;

		public SpellBook() {
		}

		public SpellBook(Integer id, String title, boolean forbidden) {
			super( id, title );
			this.forbidden = forbidden;
		}

		public boolean isForbidden() {
			return forbidden;
		}

		public void setForbidden(boolean forbidden) {
			this.forbidden = forbidden;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			if ( !super.equals( o ) ) {
				return false;
			}
			SpellBook spellBook = (SpellBook) o;
			return forbidden == spellBook.forbidden;
		}

		@Override
		public int hashCode() {
			return Objects.hash( super.hashCode(), forbidden );
		}
	}

	@Entity(name = "Novel")
	@Table(name = Novel.TABLE_NAME)
	@SoftDelete
	public static class Novel extends Book {
		private static final String TABLE_NAME = "ORMTest_NOVEL";

		private String genre;

		public Novel() {
		}

		public Novel(Integer id, String title, String genre) {
			super( id, title );
			this.genre = genre;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			if ( !super.equals( o ) ) {
				return false;
			}
			Novel novel = (Novel) o;
			return Objects.equals( genre, novel.genre );
		}

		@Override
		public int hashCode() {
			return Objects.hash( super.hashCode(), genre );
		}
	}
}
