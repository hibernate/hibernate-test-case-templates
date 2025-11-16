package org.hibernate.validator.bugs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.group.GroupSequenceProvider;

@GroupSequenceProvider(value = YourAnnotatedBeanGroupSequenceProvider.class)
public record YourAnnotatedBean(@NotNull Long id, boolean enabled,
                                @NotEmpty(groups = Enabled.class) List<String> labels) {

}
