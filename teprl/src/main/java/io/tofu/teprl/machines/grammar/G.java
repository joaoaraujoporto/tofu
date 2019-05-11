package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;

public class G {
	ArrayList<String> terminals;
	ArrayList<String> nonTerminals;
	ArrayList<Production> productions;
	
	
	public G() {
		terminals = new ArrayList<String>();
		nonTerminals = new ArrayList<String>();
		productions = new ArrayList<Production>();
	}
	
	public void addTerminal(String t) {
		terminals.add(t);
	}
	
	public void addNonTerminal(String nt) {
		nonTerminals.add(nt);
	}

	public ArrayList<String> getTerminals() {
		return terminals;
	}

	public ArrayList<String> getNonTerminals() {
		return nonTerminals;
	}
	
	public void addProduction(Production p) {
		productions.add(p);
	}
	
	public ArrayList<Production> getProductions() {
		return productions;
	}
}