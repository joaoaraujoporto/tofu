package io.tofu.tepas.functions;

import io.tofu.commons.symbol.Symbol;
import io.tofu.tepas.AttribSet;
import io.tofu.teprl.machines.grammar.Production;

public class GetAttrib extends Function<StringBuilder> {

	GetAttrib(Object ... args) {
		super(args);
	}
	
	@Override
	public StringBuilder apply() {
		Production<String,AttribSet> p = (Production<String,AttribSet>) args[0];
		String symbolName = (String) args[1];
		String attribName = (String) args[2];
		int i = 0;
		
		if (args[3] != null)
			i = (int) args[3];
			
		Symbol<String,AttribSet> s = null;
		
		if (i == 0 && p.getHead().getSign().equals(symbolName))
			return p.getHead().getMeaning().get(attribName);
		
		for (int j = 0; i < -1 && j < p.getBody().size(); j++)
			if (p.getBody().get(j).getSign().equals(symbolName)) {
				s = p.getBody().get(j);
				i--;
			}
		
		if (s == null)
			throw new RuntimeException("Not found attribute");
		
		return s.getMeaning().get(attribName);
	}
}
