package io.tofu.commons.symbol;

public class Token<T> extends Terminal<String,T>{
	public Token(String name) { super(name); }
	public Token(String name, T value) { super(name, value); }
	public String getName() { return getSign(); }
	public T getValue() { return getMeaning(); }
}
