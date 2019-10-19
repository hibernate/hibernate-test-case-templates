package org.hibernate.validator.bugs;


import org.hibernate.validator.bugs.constraints.Magic;

import javax.validation.GroupSequence;
import javax.validation.Valid;

@GroupSequence({ AnotherBean.class, Magic.class})
public class AnotherBean {

    @Valid
    private YourAnnotatedBean yourAnnotatedBean;


    public void setYourAnnotatedBean(YourAnnotatedBean yourAnnotatedBean) {
        this.yourAnnotatedBean = yourAnnotatedBean;
    }
}
