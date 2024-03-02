package org.hibernate.bugs.domain;

import jakarta.persistence.Embeddable;

import java.util.LinkedHashSet;
import java.util.Set;

@Embeddable
public class UserSettings extends LinkedHashSet<RuleDefinition> {

	public UserSettings() {
	}

	public UserSettings(Set<RuleDefinition> ruleDefinitions) {
		super.addAll(ruleDefinitions);
	}

}
