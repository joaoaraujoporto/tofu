package io.tofu.tepas;

import java.util.ArrayList;
import java.util.Stack;

import io.tofu.commons.symbol.EndOfSentence;
import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.teprl.machines.grammar.Production;

public class Recognizer {
	private Stack<Symbol<String,?>> stack;
	private TS ts;
	private TAS tas;
	private SDT sdt;

	public Recognizer(TS ts, TAS tas, SDT sdt) {
		this.ts = ts;
		this.tas = tas;
		this.sdt = sdt;
		stack = new Stack<Symbol<String,?>>();
	}

	public void clearStack() {
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
				throw new SyntaxErrorException((Token<Integer>) token, ts);
			
			 /* if (terminalOnTop instanceof EndOfSentence)
				return; // TODO - Sentence accepted */
			
			return;
		}
		
		if (top instanceof NonTerminal) {
			Production<String,AttribSet> p = tas.getEntries().get(
					new TASIndex((NonTerminal<String,AttribSet>) top,
							token));
			
			if (p == null)
				throw new SyntaxErrorException((Token<Integer>) token, ts);
			
			ArrayList<Symbol<String,AttribSet>> derived = p.getBody();
			
			for (int i = derived.size() - 1; i >= 0; i--)
				if (!derived.get(i).equals(tas.getGLC().getEpsilon()))
					stack.add(derived.get(i));
			
			for (SemanticRule sr : (ArrayList<SemanticRule>) sdt.getRules().get(p))
				if (sr.getType().equals("her"))
					sr.apply();
			
			input(token);
			
			for (SemanticRule sr : (ArrayList<SemanticRule>) sdt.getRules().get(p))
				if (sr.getType().equals("sin"))
					sr.apply();
		}
	}

	public boolean isStackEmpty() {
		return stack.isEmpty();
	}
}
