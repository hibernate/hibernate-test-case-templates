package org.hibernate.validator.bugs;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class SecondChildBean {

    @NotBlank
    private String foo;

    @NotBlank
    private String bar;

    public SecondChildBean(String foo, String bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondChildBean that = (SecondChildBean) o;
        return Objects.equals(foo, that.foo) &&
                Objects.equals(bar, that.bar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foo, bar);
    }
}
