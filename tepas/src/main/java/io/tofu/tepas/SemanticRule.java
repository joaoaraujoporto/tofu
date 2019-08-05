package io.tofu.tepas;

public abstract class SemanticRule extends Procedure {
	
	String type;
	
	SemanticRule(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
