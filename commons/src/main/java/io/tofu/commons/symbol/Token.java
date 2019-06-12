package io.tofu.commons.symbol;

public abstract class Token<T> {
	public abstract String getName();
	public abstract T getValue();
}
