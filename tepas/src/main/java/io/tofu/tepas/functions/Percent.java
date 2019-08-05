package io.tofu.tepas.functions;

public class Percent extends Function<Integer> {

	Percent(Object ... args) {
		super(args);
	}
	
	@Override
	public Integer apply() {
		int op1 = (int) args[0];		
		int op2 = (int) args[1];
		
		if (args[2] != null && args[3] != null)
			if (!((String) args[2]).equals((String) args[3]))
				throw new RuntimeException("Type not mismatch");
		
		
		return op1 % op2;
	}
	
}