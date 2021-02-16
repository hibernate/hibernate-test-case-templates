package org.hibernate.validator.bugs;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class FirstChildBean {
    @NotBlank
    private String name;

    // 3 to 4 further fields with simple validators

    @Valid
    private List<SecondChildBean> secondChildren;

    public FirstChildBean(String name, List<SecondChildBean> secondChildren) {
        this.name = name;
        this.secondChildren = secondChildren;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstChildBean that = (FirstChildBean) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(secondChildren, that.secondChildren);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, secondChildren);
    }
}
