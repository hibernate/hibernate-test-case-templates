package org.hibernate.search.bugs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed(index = "UntypedData")
@Table(name = "UntypedData", indexes = { @Index(name = "index_string", columnList = "string") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(columnDefinition = "char(2)", name = "UntypedDataType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("1")
public class UntypedDataEntity {

	int id;
	String string;
	
	public UntypedDataEntity(String string) {
		this.string = string;
	}
	public UntypedDataEntity() {
		
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
}
