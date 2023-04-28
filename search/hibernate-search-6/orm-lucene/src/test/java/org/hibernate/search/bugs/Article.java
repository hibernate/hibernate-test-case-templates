/*
 * TheVegCat - The Vegan Catalog.
 * Copyright (C) H.Lo
 * mailto:horvoje@gmail.com
 */
package org.hibernate.search.bugs;

import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.RoutingBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.AssociationInverseSide;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectPath;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyValue;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity @Indexed(routingBinder = @RoutingBinderRef(type = LoggingRoutingBridge.Binder.class))
public final class Article extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@IndexedEmbedded
	@AssociationInverseSide(inversePath = @ObjectPath(@PropertyValue(propertyName = "articles")))
	private Manufacturer manufacturer;

	protected Article() {
	}

	public Article(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
}
