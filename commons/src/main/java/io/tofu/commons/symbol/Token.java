package io.tofu.commons.symbol;

public class Token<T> extends GSymbol<T> {
	private String name;
	
	public Token(String name,T value) {
		super(value);
		this.name = name;
		this.terminal = true;
	}
	
	@Override
	public String toString() {
		return "<" + name + ", " + value + ">";
	}

	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
}