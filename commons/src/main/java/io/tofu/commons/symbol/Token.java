package io.tofu.commons.symbol;

public class Token<T> extends GSymbol<T> {
	private String name;
	
	public Token(String name, T value) {
		super(value);
		this.terminal = true;
	}
	
	@Override
	public String toString() {
		return "<" + name + ", " + value + ">";
	}

	public String getName() {
		return name;
	}

	public void setValue(T value) {
		this.value = value;
	}
}