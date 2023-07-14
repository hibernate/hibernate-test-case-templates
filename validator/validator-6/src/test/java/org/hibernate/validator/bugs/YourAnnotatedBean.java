package org.hibernate.validator.bugs;

import javax.validation.constraints.NotNull;

public class YourAnnotatedBean {

	@NotNull
	private Long id;

	private String name;

	protected YourAnnotatedBean() {
	}

	public YourAnnotatedBean(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
