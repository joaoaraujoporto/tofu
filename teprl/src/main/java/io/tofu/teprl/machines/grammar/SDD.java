package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Terminal;

public class SDD<T,R> extends GLC<T,R> {
	Map<Production<T,R>,ArrayList<SemanticRule<T,R>>> rules;
	
	public SDD(String name, NonTerminal<T,R> startSymbol, Terminal<T,R> epsilon) {
		super(name, startSymbol, epsilon);
		rules = new HashMap<Production<T,R>, ArrayList<SemanticRule<T,R>>>();
	}
	
	@Override
	public void addProduction(Production<T,R> p) {
		super.addProduction(p);
		
		ArrayList<SemanticRule<T,R>> semanticRules = new ArrayList<SemanticRule<T,R>>();
		rules.put(p, semanticRules);
	}
	
	public void addSemanticRule(Production<T,R> p, SemanticRule<T,R> sr) {
		ArrayList<SemanticRule<T,R>> semanticRules = rules.get(p);
		semanticRules.add(sr);
	}
}
