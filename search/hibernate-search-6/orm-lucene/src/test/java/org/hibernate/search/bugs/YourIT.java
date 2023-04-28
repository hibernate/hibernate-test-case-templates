package org.hibernate.search.bugs;

import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;

public class YourIT extends SearchTestBase {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[]{ AbstractEntity.class, TextId.class, Article.class, Manufacturer.class };
	}

	@Test
	public void testYourBug() {
		System.out.println("Initializing...");
		Integer manufacturerId;
		try ( Session s = getSessionFactory().openSession() ) {
			Transaction tx = s.beginTransaction();
			Manufacturer manufacturer = new Manufacturer(
					new TextId( Locale.ROOT, new String[] {
							"manufacturer-foo", "manufacturer-bar"
					} ),
					"manufacturer-name" );
			Article article1 = new Article( manufacturer );
			Article article2 = new Article( manufacturer );
			s.persist( manufacturer );
			manufacturerId = manufacturer.getId();
			s.persist( article1 );
			s.persist( article2 );
			manufacturer.getArticles().add( article1 );
			manufacturer.getArticles().add( article2 );
			tx.commit();
		}
		System.out.println("Initialized.");

		System.out.println("Updating...");
		try ( Session s = getSessionFactory().openSession() ) {
			Transaction tx = s.beginTransaction();
			Manufacturer manufacturer = s.find( Manufacturer.class, manufacturerId );
			manufacturer.setName( "newname" );
			tx.commit();
		}
		System.out.println("Updated");
	}

}
