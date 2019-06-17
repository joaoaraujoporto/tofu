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
	private Map<NonTerminal<T,R>,ArrayList<Terminal<T,R>>> firsts;
	private Map<NonTerminal<T,R>,ArrayList<Terminal<T,R>>> follows;
	private Terminal<T,R> epsilon;
	private Map<NonTerminal<T,R>,ArrayList<NonTerminal<T,R>>> dependenciesInFollow;	
	
	public GLC(String name, NonTerminal<T,R> startSymbol, Terminal<T,R> epsilon) {
		super(name);
		
		terminals = new ArrayList<Terminal<T,R>>();
		nonTerminals = new ArrayList<NonTerminal<T,R>>();
		productions = new ArrayList<Production<T,R>>();
		
		this.startSymbol = startSymbol;
		this.epsilon = epsilon; // For now, all GLCs have the epsilon symbol
		
		setFirst();
		setFollow();
		setDependenciesInFollow();
	}

	public Terminal<T,R> getEpsilon() {
		return epsilon;
	}
	
	public void addTerminal(Terminal<T,R> t) {
		terminals.add(t);
	}
	
	public void addNonTerminal(NonTerminal<T,R> nt) {
		nonTerminals.add(nt);
		firsts.put(nt, new ArrayList<Terminal<T,R>>());
		follows.put(nt, new ArrayList<Terminal<T,R>>());
		dependenciesInFollow.put(nt, new ArrayList<NonTerminal<T,R>>());
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
		return (ArrayList<Terminal<T,R>>) firsts.get(nt).clone();
	}
	
	public ArrayList<Terminal<T,R>> getFollow(NonTerminal<T,R> nt) {
		return (ArrayList<Terminal<T,R>>) follows.get(nt).clone();
	}
	
	private void setFirst() {
		firsts = new HashMap<NonTerminal<T,R>,ArrayList<Terminal<T,R>>>();
	}
	
	private void setFollow() {
		follows = new HashMap<NonTerminal<T,R>,ArrayList<Terminal<T,R>>>();
	}
	
	private void setDependenciesInFollow() {
		dependenciesInFollow = new HashMap<NonTerminal<T,R>, 
				ArrayList<NonTerminal<T,R>>>();
	}
	
	// User decides when his grammar is ready
	public void updateFirst() {
		for (NonTerminal<T,R> nt : nonTerminals)
			first(nt);
	}
	
	public void updateFollow(Terminal<T,R> endOfSentence) {
		ArrayList<Terminal<T,R>> follow = 
				follows.get(startSymbol);
			
		follow.add(endOfSentence);
			
		for (Production<T,R> p : productions) {
			ArrayList<Symbol<T,R>> body = p.getBody();

			//follow.addAll(follows.get(p.getHead()));
			for (int i = 0; i < body.size(); i++) {
				Symbol<T,R> s = body.get(i);
				
				if (s instanceof Terminal)
					continue;
				
				NonTerminal<T,R> nt = (NonTerminal<T,R>) s;
				follow = follows.get(nt);
				ArrayList<Symbol<T,R>> subBody = Expander.subword(body, i + 1);
				ArrayList<Terminal<T,R>> firstSub = first(subBody);
				
				if (subBody.isEmpty() || firstSub.contains(epsilon)) {
					addFreshes(follow, follows.get(p.getHead()));
					ArrayList<NonTerminal<T,R>> dependencies =
							dependenciesInFollow.get(p.getHead());
					addFresh(dependencies, p.getHead());
				}
				
				addFreshes(follow, firstSub);
				updateDependencies(nt);
			}
		}
	}
	
	private <E,F extends E> void addFreshes(ArrayList<E> forArray,
			ArrayList<F> fromArray) {
		for (F element : fromArray)
			addFresh(forArray, element);
		
	}
	
	private <E,F extends E> void addFresh(ArrayList<E> forArray, F element) {
		if (!forArray.contains(element)) forArray.add(element);
	}
	
	private void updateDependencies(NonTerminal<T, R> nt) {
		ArrayList<NonTerminal<T,R>> dependencies = dependenciesInFollow.get(nt);
		ArrayList<Terminal<T,R>> follow = follows.get(nt);
		
		for (NonTerminal<T,R> d : dependencies) {
			ArrayList<Terminal<T,R>> dFollow = follows.get(d);
			addFreshes(follow, dFollow);
		}
	}
	
	private ArrayList<Terminal<T,R>> first(NonTerminal<T,R> nt) {
		ArrayList<Production<T,R>> productions = getProductions(nt);		
		ArrayList<Terminal<T,R>> first = this.firsts.get(nt);
		
		for (Production<T,R> p : productions)
			addFreshes(first, first(p.getBody()));
		
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