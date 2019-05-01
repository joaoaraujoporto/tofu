package io.tofu.commons.symbol;

/*<<<<<<< HEAD
public class Token<T> extends GSymbol<T> {
	private String name;
	
	public Token(String name, T value) {
		super(value);
		this.terminal = true;
=======*/
public class Token<T> implements Terminal {
	private String name;
	private T value;
	
	public Token(String name, T value) {
		this.name = name;
		this.value = value;
//>>>>>>> improv
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