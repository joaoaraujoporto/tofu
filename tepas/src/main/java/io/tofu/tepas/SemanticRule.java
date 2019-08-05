package io.tofu.tepas;

import io.tofu.tepas.procedures.Procedure;

public class SemanticRule {
	
	Procedure procedure;
	String type;
	
	SemanticRule(Procedure procedure, String type) {
		this.procedure = procedure;
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public void apply() {
		procedure.apply();
	}
}
