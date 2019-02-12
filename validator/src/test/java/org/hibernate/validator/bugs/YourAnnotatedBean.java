package org.hibernate.validator.bugs;

import org.hibernate.validator.bugs.constraints.Magic;

import javax.validation.GroupSequence;
import javax.validation.Valid;

@GroupSequence({ YourAnnotatedBean.class, Magic.class})
public class YourAnnotatedBean {

	@Valid
	private AnotherBean bean;

	public void setBean(AnotherBean bean) {
		this.bean = bean;
	}
}
