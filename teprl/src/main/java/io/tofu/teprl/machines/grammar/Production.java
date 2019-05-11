package io.tofu.teprl.machines.grammar;
import java.util.ArrayList;

public class Production {
	private Symbol head;
	private ArrayList<Symbol> body;
	
	public Production(Symbol head, ArrayList<Symbol> body) {
		this.head = head;
		this.body = body;
	}
	
	public Symbol getHead() {
		return head;
	}
	
	public ArrayList<Symbol> getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		String b = new String();
		
		for (Symbol s : body)
			b += s.getValue();
		
		return head.getValue() + " -> " + b;
	}
}