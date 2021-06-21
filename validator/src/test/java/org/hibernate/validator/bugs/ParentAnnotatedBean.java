package org.hibernate.validator.bugs;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class ParentAnnotatedBean {

	@NotBlank
	@Size
	private String name;

	@Valid
	private List<FirstChildBean> firstChildren;

	protected ParentAnnotatedBean() {}

	public ParentAnnotatedBean(String name, List<FirstChildBean> firstChildren) {
		this.name = name;
		this.firstChildren = firstChildren;
	}

	public List<FirstChildBean> getFirstChildren() { return firstChildren; }

	public void setFirstChildren(List<FirstChildBean> firstChildren) { this.firstChildren = firstChildren; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
