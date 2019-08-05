package io.tofu.tepas.procedures;

public class Assignment extends Procedure {

	public Assignment(Object ... args) {
		super(args);
	}
	
	@Override
	public void apply() {
		StringBuilder lvalue = (StringBuilder) args[0];
		lvalue.append((String) args[1]);
	}

}
