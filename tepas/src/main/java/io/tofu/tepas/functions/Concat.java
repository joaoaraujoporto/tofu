package io.tofu.tepas.functions;

public class Concat extends Function<String> {

	Concat(Object ... args) {
		super(args);
	}
	
	@Override
	public String apply() {
		StringBuilder r = new StringBuilder();
		
		String[] strings = (String[]) args;
		
		for (String s : strings)
			if (!s.equals("ws"))
				r.append(s);
			
		return r.toString();
	}

	
}
