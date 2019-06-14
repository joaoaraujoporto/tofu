package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;

public class GLC<T,R> extends Grammar<T,R> {
	private ArrayList<Terminal<T,R>> terminals;
	private ArrayList<NonTerminal<T,R>> nonTerminals;
	private ArrayList<Production<T,R>> productions;
	private NonTerminal<T,R> startSymbol;
	private Map<NonTerminal<T,R>,ArrayList<Terminal<T,R>>> first;
	private Map<NonTerminal<T,R>,ArrayList<Terminal<T,R>>> follow;
	private Terminal<T,R> epsilon;
	
	public GLC(String name, NonTerminal<T,R> startSymbol, Terminal<T,R> epsilon) {
		super(name);
		
		terminals = new ArrayList<Terminal<T,R>>();
		nonTerminals = new ArrayList<NonTerminal<T,R>>();
		productions = new ArrayList<Production<T,R>>();
		this.startSymbol = startSymbol;
		this.epsilon = epsilon; // For now, all GLCs have the epsilon symbol
		
		follow = new HashMap<NonTerminal<T,R>,ArrayList<Terminal<T,R>>>();	
		setFirst();
	}
	
	public Terminal<T,R> getEpsilon() {
		return epsilon;
	}
	
	public void addTerminal(Terminal<T,R> t) {
		terminals.add(t);
	}
	
	public void addNonTerminal(NonTerminal<T,R> nt) {
		nonTerminals.add(nt);
		first.put(nt, new ArrayList<Terminal<T,R>>());
	}

	public ArrayList<Terminal<T,R>> getTerminals() {
		ArrayList<Terminal<T,R>> terminals = new ArrayList<Terminal<T,R>>(); 
		
		for (Terminal<T,R> t : this.terminals)
			if (!terminals.contains(t))
				terminals.add(t);
		
		return terminals;
	}

	public ArrayList<NonTerminal<T,R>> getNonTerminals() {
		ArrayList<NonTerminal<T,R>> nonTerminals = new ArrayList<NonTerminal<T,R>>(); 
		
		for (NonTerminal<T,R> nt : this.nonTerminals)
			if (!nonTerminals.contains(nt))
				nonTerminals.add(nt);
		
		return nonTerminals;
	}
	
	public void addProduction(Production<T,R> p) {
		addNonTerminal(p.getHead());
		
		for (Symbol<T,R> s : p.getBody()) {
			if (s instanceof NonTerminal)
				addNonTerminal((NonTerminal<T,R>) s);
			else if (s instanceof Terminal)
				addTerminal((Terminal<T,R>) s);
		}
		
		productions.add(p);
	}
	
	public ArrayList<Production<T,R>> getProductions() {
		return productions;
	}
	
	public ArrayList<Production<T,R>> getProductions(Symbol<T,R> head) {
		ArrayList<Production<T,R>> prods = new ArrayList<Production<T,R>>();
		
		for (Production<T,R> p : productions)
			if (p.getHead().equals(head))
				prods.add(p);
		
		return prods;
	}
	
	public void removeProduction(int i) {
		removeProduction(productions.get(i));
	}
	
	public void removeProduction(Production<T,R> p) {
		nonTerminals.remove(p.getHead());
		
		for (Symbol<T,R> s : p.getBody()) {
			if (s instanceof NonTerminal)
				nonTerminals.remove(s);
			else
				terminals.remove(s);
		}
		
		productions.remove(p);
	}
	
	public ArrayList<Terminal<T,R>> getFirst(NonTerminal<T,R> nt) {
		return (ArrayList<Terminal<T,R>>) first.get(nt).clone();
	}
	
	public ArrayList<Terminal<T,R>> getFollow(NonTerminal<T,R> nt) {
		// TODO return (ArrayList<Terminal>) follow(nt).clone();
		return null;
	}
	
	private void setFirst() {
		first = new HashMap<NonTerminal<T,R>,ArrayList<Terminal<T,R>>>();
	}
	
	// User decides when his grammar is ready
	public void updateFirst() {
		for (NonTerminal<T,R> nt : nonTerminals)
			first(nt);
	}
	
	private ArrayList<Terminal<T,R>> first(NonTerminal<T,R> nt) {
		ArrayList<Production<T,R>> productions = getProductions(nt);		
		ArrayList<Terminal<T,R>> first = this.first.get(nt);
		
		for (Production<T,R> p : productions)
			for (Terminal<T,R> t : first(p.getBody()))
				if (!first.contains(t))
					first.add(t);
		
		return first;
	}
	
	public ArrayList<Terminal<T,R>> getFirst(ArrayList<Symbol<T,R>> sentence) {
		ArrayList<Terminal<T,R>> first = new ArrayList<Terminal<T,R>>();
		
		if (sentence.isEmpty())
			return first;
		
		Symbol<T,R> s = sentence.get(0);
		
		if (s instanceof Terminal)
			first.add((Terminal<T,R>) s);
		
		if (s instanceof NonTerminal) {
			first.addAll(getFirst((NonTerminal<T,R>) s));
			
			if (first.contains(epsilon)) {
				first.remove(epsilon);
				ArrayList<Terminal<T,R>> r = getFirst(Expander.subword(sentence, 1));
				first.addAll(r);
				
				if (r.isEmpty())
					first.add(epsilon);			
			}
		}
		
		return first;
	}
	
	private ArrayList<Terminal<T,R>> first(ArrayList<Symbol<T,R>> sentence) {
		ArrayList<Terminal<T,R>> first = new ArrayList<Terminal<T,R>>();
		
		if (sentence.isEmpty())
			return first;
		
		Symbol<T,R> s = sentence.get(0);
		
		if (s instanceof Terminal)
			first.add((Terminal<T,R>) s);
		
		if (s instanceof NonTerminal) {
			first.addAll(first((NonTerminal<T,R>) s));
			
			if (first.contains(epsilon)) {
				first.remove(epsilon);
				ArrayList<Terminal<T,R>> r = first(Expander.subword(sentence, 1));
				first.addAll(r);
				
				if (r.isEmpty())
					first.add(epsilon);
			}
		}
		
		return first;
	}

	public NonTerminal<T,R> getStartSymbol() {
		return startSymbol;
	}
}