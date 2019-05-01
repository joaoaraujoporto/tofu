package io.tofu.commons.symbol;

public class Token<T> implements Terminal {
	private String name;
	private T value;
	
	public Token(String name, T value) {
		this.name = name;
		this.value = value;
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