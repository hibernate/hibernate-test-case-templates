package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.bugs.hhh15110.model.Comment;
import org.hibernate.bugs.hhh15110.model.Post;
import org.hibernate.bugs.hhh15110.model.User;
import org.junit.*;

import java.util.Date;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	public static final long POST_1_ID = 1L;
	public static final long POST_2_ID = 2L;
	public static final Long USER_ID_VALUE = 111L;
	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {

		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		//assets
		entityManager.getTransaction().begin();
		//add 2 posts
		Post post1 = new Post();
		post1.setPostId(POST_1_ID);
		entityManager.persist(post1);
		//**********
		Post post2 = new Post();
		post2.setPostId(POST_2_ID);
		entityManager.persist(post2);

		//add comments1 on post1 by user1
		User user1  = new User();
		user1.setUserId(USER_ID_VALUE);
		entityManager.persist(user1);
		Comment comment1 = new Comment();
		comment1.setCommentId(11L);
		comment1.setPost(post1);
		comment1.setUser(user1);
	//	post1.getComments().add(comment1);
	//	user1.getComments().add(comment1);
		entityManager.persist(comment1);
		entityManager.getTransaction().commit();
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	@Test
	public void subqueryEqualsObject() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String jpqlUpdatePost1ViaUser1 = "UPDATE Post postAlias " +
				"SET postAlias.someDate = :someDateParam " +
				"WHERE EXISTS " +
				"(SELECT commentAlias " +
				"FROM Comment commentAlias " +
				"WHERE commentAlias.user.userId = :userIdParam " +
				"AND commentAlias.post = postAlias)";
		Query query = entityManager.createQuery(jpqlUpdatePost1ViaUser1);
		final Date someDateValue = new Date();
		query.setParameter("someDateParam", someDateValue);
		query.setParameter("userIdParam",USER_ID_VALUE);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		//assert that post1 has a new SomeDate value but not post2
		Assert.assertEquals(someDateValue,entityManager.find(Post.class,POST_1_ID).getSomeDate());
		Assert.assertNull(entityManager.find(Post.class,POST_2_ID).getSomeDate());
		entityManager.close();
	}

	@Test
	public void subqueryEqualsIdField() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String jpqlUpdatePost1ViaUser1 = "UPDATE Post postAlias " +
				"SET postAlias.someDate = :someDateParam " +
				"WHERE EXISTS " +
				"(SELECT commentAlias " +
				"FROM Comment commentAlias " +
				"WHERE commentAlias.user.userId = :userIdParam " +
				"AND commentAlias.post.postId = postAlias.postId)";
		Query query = entityManager.createQuery(jpqlUpdatePost1ViaUser1);
		final Date someDateValue = new Date();
		query.setParameter("someDateParam", someDateValue);
		query.setParameter("userIdParam",USER_ID_VALUE);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		//assert that post1 has a new SomeDate value but not post2
		Assert.assertEquals(someDateValue,entityManager.find(Post.class,POST_1_ID).getSomeDate());
		Assert.assertNull(entityManager.find(Post.class,POST_2_ID).getSomeDate());
		entityManager.close();
	}

	@Test
	public void selectWithsubqueryEqualsObject() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String jpqlUpdatePost1ViaUser1 = "SELECT postAlias " +
				" FROM Post postAlias "+
				"WHERE EXISTS " +
				"(SELECT commentAlias " +
				"FROM Comment commentAlias " +
				"WHERE commentAlias.user.userId = :userIdParam " +
				"AND commentAlias.post = postAlias)";
		Query query = entityManager.createQuery(jpqlUpdatePost1ViaUser1);
		query.setParameter("userIdParam",USER_ID_VALUE);
		Assert.assertEquals(1,query.getResultList().size());
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	@Ignore ("fatto così da errore perché saltano le FK di Comment")
	public void deleteWithSubqueryEqualsObject() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		String jpqlUpdatePost1ViaUser1 = "DELETE Post postAlias " +
				"WHERE EXISTS " +
				"(SELECT commentAlias " +
				"FROM Comment commentAlias " +
				"WHERE commentAlias.user.userId = :userIdParam " +
				"AND commentAlias.post = postAlias)";
		Query query = entityManager.createQuery(jpqlUpdatePost1ViaUser1);
		query.setParameter("userIdParam",USER_ID_VALUE);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		Assert.assertNull(entityManager.find(Post.class,POST_1_ID));
		Assert.assertNotNull(entityManager.find(Post.class,POST_2_ID));
		entityManager.close();
	}

}
