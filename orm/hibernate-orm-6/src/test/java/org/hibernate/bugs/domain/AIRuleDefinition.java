package org.hibernate.bugs.domain;

import java.util.UUID;

public class AIRuleDefinition extends RuleDefinition {

	private UUID ruleDefinitionId;
	private String ruleName;

	public UUID getRuleDefinitionId() {
		return ruleDefinitionId;
	}

	public void setRuleDefinitionId(UUID ruleDefinitionId) {
		this.ruleDefinitionId = ruleDefinitionId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Override
	public String getRuleType() {
		return "AI";
	}
}