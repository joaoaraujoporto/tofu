package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;

public class G {
	ArrayList<Terminal> terminals;
	ArrayList<NonTerminal> nonTerminals;
	ArrayList<Production> productions;
	
	
	public G() {
		terminals = new ArrayList<Terminal>();
		nonTerminals = new ArrayList<NonTerminal>();
		productions = new ArrayList<Production>();
	}
	
	public void addTerminal(Terminal t) {
		terminals.add(t);
	}
	
	public void addNonTerminal(NonTerminal nt) {
		nonTerminals.add(nt);
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
				addNonTerminal((NonTerminal )s);
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
}