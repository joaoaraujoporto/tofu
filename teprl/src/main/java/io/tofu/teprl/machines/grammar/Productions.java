package io.tofu.teprl.machines.grammar;

import java.util.Collection;

import io.tofu.commons.symbol.GSymbol;

public class Productions<T> {
	GSymbol<T> head;
	Collection<GSymbol<T>> body;
	
	public Productions(GSymbol<T> head, Collection<GSymbol<T>> body) {
		setHead(head);
	}
	
	public void setHead(GSymbol<T> head) {
		if (!head.isTerminal())
			return; // TODO - return exception
		
		this.head = head;
	}
	
	public void setBody(Collection<GSymbol<T>> body) {
		
	}
}
