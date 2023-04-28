/*
 * TheVegCat - The Vegan Catalog.
 * Copyright (C) H.Lo
 * mailto:horvoje@gmail.com
 */
package org.hibernate.search.bugs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.RoutingBinderRef;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.ValueBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
@Indexed(routingBinder = @RoutingBinderRef(type = LoggingRoutingBridge.Binder.class))
public class Manufacturer extends AbstractEntity {

	private String name;

	@FullTextField(valueBridge = @ValueBridgeRef(type = TextIdBridge.class))
	private TextId textId;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "manufacturer_id")
	private List<Article> articles = new ArrayList<>();

	protected Manufacturer() {
	}

	public Manufacturer(TextId textId, String name) {
		this.textId = textId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TextId getTextId() {
		return textId;
	}

	public void setTextId(TextId textId) {
		this.textId = textId;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
