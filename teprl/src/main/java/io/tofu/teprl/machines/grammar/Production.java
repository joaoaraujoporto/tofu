package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;

public class Production<T,R> {
	private NonTerminal<T,R> head;
	private ArrayList<Symbol<T,R>> body;
	
	public Production(NonTerminal<T,R> head, ArrayList<Symbol<T,R>> body) {
		this.head = head;
		this.body = body;
	}
	
	public NonTerminal<T,R> getHead() {
		return head;
	}
	
	public ArrayList<Symbol<T,R>> getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		String b = new String();
		
		for (Symbol<T,R> s : body)
			b += s.getMeaning().toString();
		
		return head.getMeaning().toString() + " -> " + b;
	}
}