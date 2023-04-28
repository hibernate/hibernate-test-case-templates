package org.hibernate.search.bugs;

import org.hibernate.search.mapper.pojo.bridge.RoutingBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.RoutingBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.RoutingBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.RoutingBridgeRouteContext;
import org.hibernate.search.mapper.pojo.route.DocumentRoutes;

public class LoggingRoutingBridge<T> implements RoutingBridge<T> {
	public static class Binder implements RoutingBinder {
		@Override
		public void bind(final RoutingBindingContext context) {
			context.dependencies().useRootOnly();
			context.bridge(Object.class, new LoggingRoutingBridge<>());
		}
	}

	@Override
	public void route(final DocumentRoutes routes, final Object entityIdentifier, final T indexedEntity, final RoutingBridgeRouteContext context) {
		System.out.println("Indexing " + indexedEntity);
		routes.addRoute();
	}

	@Override
	public void previousRoutes(final DocumentRoutes routes, final Object entityIdentifier, final T indexedEntity, final RoutingBridgeRouteContext context) {
		routes.addRoute();
	}

}
