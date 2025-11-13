package org.hibernate.validator.bugs;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.group.GroupSequenceProvider;

@GroupSequenceProvider(value = YourAnnotatedBeanGroupSequenceProvider.class)
public record YourAnnotatedBean(@NotNull(groups = Enabled.class) Long id, boolean enabled, @NotNull(groups = Enabled.class) List<String> labels) {

}
