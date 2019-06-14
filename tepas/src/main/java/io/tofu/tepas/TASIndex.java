package io.tofu.tepas;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Terminal;

public class TASIndex {
	private NonTerminal<?,?> nonTerminal;
	private Terminal<?,?> terminal;
	
	public TASIndex(NonTerminal<?,?> nonTerminal,
			Terminal<?,?> terminal) {
		this.nonTerminal = nonTerminal;
		this.terminal = terminal;
	}

	public NonTerminal<?,?> getNonTerminal() {
		return nonTerminal;
	}

	public Terminal<?,?> getTerminal() {
		return terminal;
	}
	
	@Override
	public boolean equals(Object o) {
		TASIndex tasIndex = (TASIndex) o;
		
		if (nonTerminal.equals(tasIndex.getNonTerminal()))
			if (terminal.equals(tasIndex.getTerminal()))
				return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return nonTerminal.hashCode() + terminal.hashCode();
	}
}
