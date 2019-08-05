package io.tofu.tepas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Terminal;
import io.tofu.teprl.machines.grammar.GLC;
import io.tofu.teprl.machines.grammar.Production;

public class SDT<T,R> extends GLC<T,R> {
	private Map<Production<T,R>,ArrayList<SemanticRule>> rules;

	public SDT(String name, NonTerminal<T,R> startSymbol, Terminal<T,R> epsilon) {
		super(name, startSymbol, epsilon);
		rules = new HashMap<Production<T,R>, ArrayList<SemanticRule>>();
	}
	
	public Map<Production<T, R>, ArrayList<SemanticRule>> getRules() {
		return rules;
	}
	
	@Override
	public void addProduction(Production<T,R> p) {
		super.addProduction(p);
		
		ArrayList<SemanticRule> semanticRules = new ArrayList<SemanticRule>();
		rules.put(p, semanticRules);
	}
	
	public void addSemanticRule(Production<T,R> production, SemanticRule semanticRule) {
		ArrayList<SemanticRule> semanticRules = rules.get(production);
		semanticRules.add(semanticRule);
	}
}
