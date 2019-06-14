package io.tofu.tepas;

import java.util.ArrayList;
import java.util.Stack;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import io.tofu.commons.symbol.Token;
import io.tofu.teprl.machines.grammar.Production;

public class Recognizer {
	private Stack<Symbol<String,?>> stack;
	private TAS tas;

	public Recognizer(TAS tas) {
		this.tas = tas;
	}

	public void clearStack() {
		addToStack(new EndOfSentence());
		stack.clear();
	}

	public void addToStack(Symbol<String,?> symbol) {
		stack.add(symbol);
	}

	public void input(Token<?> token) throws SyntaxErrorException {
		Symbol<String,?> top = stack.pop();
		
		if (top instanceof Terminal) {
			Terminal<String,?> terminalOnTop = 
					(Terminal<String,?>) top;
			
			/*
			 * Assumption: Each token always has a terminal
			 * correspondent in grammar with the same name.
			 * How to use PositionInCode but not turn code
			 * non-general?  
			 */
			if (!terminalOnTop.equals(token))
				throw new SyntaxErrorException(token.getName());
			
			 /* if (terminalOnTop instanceof EndOfSentence)
				return; // TODO - Sentence accepted */
			
			return;
		}
		
		if (top instanceof NonTerminal) {
			Production<String,AttribSet> p = tas.getEntries().get(
					new TASIndex((NonTerminal<String,?>) top,
							token));
			
			if (p == null)
				throw new SyntaxErrorException(token.getName());
			
			ArrayList<Symbol<String,AttribSet>> derived = p.getBody();
			
			for (int i = derived.size() - 1; i >= 0; i--)
				stack.add(derived.get(i));
		}
	}

	public boolean isStackEmpty() {
		return stack.isEmpty();
	}
}
