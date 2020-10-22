package org.hibernate.search.bugs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@DiscriminatorValue("2")
@Indexed
public class PersonEntity extends UntypedDataEntity {

	private String name;
	private String addressLine;
	private String zip;
	private String country;
	
	public PersonEntity(String string, String name, String addressLine, String zip, String country) {
		super(string);
		this.name = name;
		this.addressLine = addressLine;
		this.zip = zip;
		this.country = country;
	}
	
	public PersonEntity() {
		
	}
	
	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		return "Person - Name: "+name+" Adress: "+addressLine+" Zip: "+zip+" Country: "+country;
	}
}
