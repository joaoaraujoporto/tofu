package io.tofu.commons.symbol;

public class GSymbol<T> extends Symbol<T> {
	protected boolean terminal;
	
	public GSymbol(T value) {
		super(value);
	}
	
	public boolean isTerminal() {
		return terminal;
	}
}
