package org.hibernate.bugs.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UnrelatedObject implements Serializable
{
	private static final long serialVersionUID = 6204295893771779619L;

	@Id
	@Column(
		length = 16,
		unique = true,
		nullable = false,
		insertable = true,
		updatable = false)
	private UUID id = UUID.randomUUID();

	@ManyToOne
	private Child child;

	private String data;

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		UnrelatedObject other = (UnrelatedObject) obj;
		return Objects.equals(id, other.id);
	}

	public Child getChild()
	{
		return child;
	}

	public void setChild(Child child)
	{
		this.child = child;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public UUID getId()
	{
		return id;
	}
}
