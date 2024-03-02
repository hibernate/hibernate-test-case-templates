package org.hibernate.bugs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "clazz")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AIRuleDefinition.class, name = "AI_RULE")
})
public abstract class RuleDefinition {
	@JsonIgnore
	public abstract String getRuleType();
	public abstract void setRuleName(String name);

	public abstract String getRuleName();
}