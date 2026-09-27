package org.hibernate.bugs;

import java.util.List;
import org.hibernate.bugs.entities.ChildEntity;
import org.hibernate.bugs.entities.AnotherEntity;
import org.hibernate.bugs.entities.ParentEntity;
import org.hibernate.cfg.AvailableSettings;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@DomainModel(annotatedClasses = {
		AnotherEntity.class,
		ParentEntity.class,
		ChildEntity.class,
})
@ServiceRegistry(settings = {
		@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
		@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),
})
@SessionFactory
class ORMUnitTestCase {

	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
		// 1st tx : parent initialization with 2 children "a1" and "a2".
		var parentId = scope.fromTransaction(session -> {
			var parent = new ParentEntity();
			parent.replaceChildren(List.of(new ChildEntity("a1"), new ChildEntity("a2")));
			session.persist(parent);
			return parent.getId();
		});

		scope.inTransaction(session -> {
			for (var i = 0; i < 3; i++) {
				// In the real-life scenario, we persist a new AnotherEntity before selecting a
				// bunch of them, but it turns out
				// it's not actually needed to reproduce.

				// execute a HQL query that may trigger a "flush" but is not directly related to
				// "ChildEntity".
				session.createSelectionQuery("select p.id from AnotherEntity p", String.class).getResultList();

				// attempt to remove current children and replace them with new ones.
				var parent = session.find(ParentEntity.class, parentId);
				parent.replaceChildren(List.of(new ChildEntity("b" + i), new ChildEntity("c" + i)));
			}
		});

		scope.inTransaction(session -> {
			var parent = session.createSelectionQuery("select p from ParentEntity p", ParentEntity.class)
					.getSingleResult();
			var allChildren = session.createSelectionQuery("select c from ChildEntity c", ChildEntity.class)
					.getResultList();

			// actual is 6 if annotated with @JoinColumn(updatable=false), 2 otherwise
			Assertions.assertEquals(2, parent.getChildren().size());

			// actual is always 6. Only "a1" and "a2" have actually been deleted.
			Assertions.assertEquals(2, allChildren.size());
		});
	}
}
