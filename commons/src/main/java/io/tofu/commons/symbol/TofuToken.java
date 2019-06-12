package io.tofu.commons.symbol;

public class TofuToken<T> extends Token<T> {
	private String name;
	private T value;
	
	public TofuToken(String name,T value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "<" + name + ", " + value.toString() + ">";
	}

	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
}