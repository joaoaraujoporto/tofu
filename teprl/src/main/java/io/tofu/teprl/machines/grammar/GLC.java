package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.tofu.teprl.machines.Machine;

public class GLC extends Grammar {
	private ArrayList<Terminal> terminals;
	private ArrayList<NonTerminal> nonTerminals;
	private ArrayList<Production> productions;
	private NonTerminal startSymbol;
	private Map<NonTerminal,ArrayList<Terminal>> first;
	private Map<NonTerminal,ArrayList<Terminal>> follow;
	
	public GLC(String name, NonTerminal startSymbol) {
		super(name);
		
		terminals = new ArrayList<Terminal>();
		nonTerminals = new ArrayList<NonTerminal>();
		productions = new ArrayList<Production>();
		startSymbol = startSymbol;
		
		follow = new HashMap<NonTerminal,ArrayList<Terminal>>();	
		setFirst();
	}
	
	public void addTerminal(Terminal t) {
		terminals.add(t);
	}
	
	public void addNonTerminal(NonTerminal nt) {
		nonTerminals.add(nt);
		first.put(nt, new ArrayList<Terminal>());
	}
	
	public void addTerminal(String t) {
		addTerminal(new Terminal(t));
	}
	
	public void addNonTerminal(String nt) {
		addNonTerminal(new NonTerminal(nt));
	}

	public ArrayList<Terminal> getTerminals() {
		ArrayList<Terminal> terminals = new ArrayList<Terminal>(); 
		
		for (Terminal t : this.terminals)
			if (!terminals.contains(t))
				terminals.add(t);
		
		return terminals;
	}

	public ArrayList<NonTerminal> getNonTerminals() {
		ArrayList<NonTerminal> nonTerminals = new ArrayList<NonTerminal>(); 
		
		for (NonTerminal nt : this.nonTerminals)
			if (!nonTerminals.contains(nt))
				nonTerminals.add(nt);
		
		return nonTerminals;
	}
	
	public void addProduction(Production p) {
		addNonTerminal(p.getHead());
		
		for (Symbol s : p.getBody()) {
			if (s instanceof NonTerminal)
				addNonTerminal((NonTerminal) s);
			else
				addTerminal((Terminal) s);
		}
		
		productions.add(p);
	}
	
	public ArrayList<Production> getProductions() {
		return productions;
	}
	
	public ArrayList<Production> getProductions(Symbol head) {
		ArrayList<Production> prods = new ArrayList<Production>();
		
		for (Production p : productions)
			if (p.getHead().equals(head))
				prods.add(p);
		
		return prods;
	}
	
	public void removeProduction(int i) {
		removeProduction(productions.get(i));
	}
	
	public void removeProduction(Production p) {
		nonTerminals.remove(p.getHead());
		
		for (Symbol s : p.getBody()) {
			if (s instanceof NonTerminal)
				nonTerminals.remove(s);
			else
				terminals.remove(s);
		}
		
		productions.remove(p);
	}
	
	public ArrayList<Terminal> getFirst(NonTerminal nt) {
		return (ArrayList<Terminal>) first.get(nt).clone();
	}
	
	public ArrayList<Terminal> getFollow(NonTerminal nt) {
		// TODO return (ArrayList<Terminal>) follow(nt).clone();
		return null;
	}
	
	private void setFirst() {
		first = new HashMap<NonTerminal,ArrayList<Terminal>>();
	}
	
	// User decides when his grammar is ready
	public void updateFirst() {
		for (NonTerminal nt : nonTerminals)
			first(nt);
	}
	
	private ArrayList<Terminal> first(NonTerminal nt) {
		ArrayList<Production> productions = getProductions(nt);		
		ArrayList<Terminal> first = this.first.get(nt);
		
		for (Production p : productions)
			for (Terminal t : first(p.getBody()))
				if (!first.contains(t))
					first.add(t);
		
		return first;
	}
	
	public ArrayList<Terminal> getFirst(ArrayList<Symbol> sentence) {
		ArrayList<Terminal> first = new ArrayList<Terminal>();
		
		if (sentence.isEmpty())
			return first;
		
		Symbol s = sentence.get(0);
		
		if (s instanceof Terminal)
			first.add((Terminal) s);
		
		if (s instanceof NonTerminal) {
			first.addAll(getFirst((NonTerminal) s));
			
			if (first.contains(Grammar.EPSILON)) {
				first.remove(Grammar.EPSILON);
				ArrayList<Terminal> r = getFirst(Expander.subword(sentence, 1));
				first.addAll(r);
				
				if (r.isEmpty())
					first.add(Grammar.EPSILON);				
			}
		}
		
		return first;
	}
	
	private ArrayList<Terminal> first(ArrayList<Symbol> sentence) {
		ArrayList<Terminal> first = new ArrayList<Terminal>();
		
		if (sentence.isEmpty())
			return first;
		
		Symbol s = sentence.get(0);
		
		if (s instanceof Terminal)
			first.add((Terminal) s);
		
		if (s instanceof NonTerminal) {
			first.addAll(first((NonTerminal) s));
			
			if (first.contains(Grammar.EPSILON)) {
				first.remove(Grammar.EPSILON);
				ArrayList<Terminal> r = first(Expander.subword(sentence, 1));
				first.addAll(r);
				
				if (r.isEmpty())
					first.add(Grammar.EPSILON);
			}
		}
		
		return first;
	}

	public NonTerminal getStartSymbol() {
		return startSymbol;
	}
}