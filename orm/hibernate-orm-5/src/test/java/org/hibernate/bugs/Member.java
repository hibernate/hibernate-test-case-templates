package org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "person" )
public class Member
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id", updatable = false, nullable = false )
	private Long id;

	private String firstName;
	private String lastName;
	private String nickName;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName( String nickName )
	{
		this.nickName = nickName;
	}
}
