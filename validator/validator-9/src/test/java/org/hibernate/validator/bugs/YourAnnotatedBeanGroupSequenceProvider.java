package org.hibernate.validator.bugs;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class YourAnnotatedBeanGroupSequenceProvider implements DefaultGroupSequenceProvider<YourAnnotatedBean> {

  @Override
  public List<Class<?>> getValidationGroups(YourAnnotatedBean object) {
    var groups = new ArrayList<Class<?>>();
    groups.add(YourAnnotatedBean.class);

    if (object == null) {
      return groups;
    }

    if (object.enabled()) {
      groups.add(Enabled.class);
    }

    return groups;
  }
}
