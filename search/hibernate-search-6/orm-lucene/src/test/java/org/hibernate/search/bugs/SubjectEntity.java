package org.hibernate.search.bugs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@DiscriminatorValue("3")
@Indexed
public class SubjectEntity extends UntypedDataEntity {


	private String subject;
	
	public SubjectEntity() {
		
	}

	protected SubjectEntity(String string, String subject) {
		super(string);
		this.subject = subject;
	}

	@Column(columnDefinition = "varchar(4000)")
	@FullTextField(analyzer = "default",projectable = Projectable.YES)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String toString() {
		return "Subject - subject: "+subject;
	}

}
