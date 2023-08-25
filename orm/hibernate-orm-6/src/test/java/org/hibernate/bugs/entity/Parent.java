package org.hibernate.bugs.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "deleted_on is null")
public abstract class Parent implements Serializable
{
	private static final long serialVersionUID = -5144633997176029629L;

	@Id
	@Column(
		length = 16,
		unique = true,
		nullable = false,
		insertable = true,
		updatable = false)
	private UUID id = UUID.randomUUID();

	private String data;

	@Column(name = "deleted_on")
	private Instant deletedOn;

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
		Parent other = (Parent) obj;
		return Objects.equals(id, other.id);
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public Instant getDeletedOn()
	{
		return deletedOn;
	}

	public void setDeletedOn(Instant deletedOn)
	{
		this.deletedOn = deletedOn;
	}

	public UUID getId()
	{
		return id;
	}
}
