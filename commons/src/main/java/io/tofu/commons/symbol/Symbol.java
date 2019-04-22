package io.tofu.commons.symbol;

public class Symbol<T> {
	protected T value;
	
	public Symbol(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
