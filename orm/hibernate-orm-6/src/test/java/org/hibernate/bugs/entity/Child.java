package org.hibernate.bugs.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Child extends Parent
{
	private static final long serialVersionUID = -7153892484343816950L;

	@Column(name = "child_data")
	private String childData;

	@OneToMany(mappedBy = "child")
	private List<PrimaryObject> primaryObjects = new ArrayList<>();

	@OneToMany(mappedBy = "child")
	private List<SecondaryObject> secondaryObjects = new ArrayList<>();

	public String getChildData()
	{
		return childData;
	}

	public void setChildData(String childData)
	{
		this.childData = childData;
	}

	public List<PrimaryObject> getPrimaryObjects()
	{
		return primaryObjects;
	}

	public List<SecondaryObject> getSecondaryObjects()
	{
		return secondaryObjects;
	}
}
