package io.tofu.commons.symbol;

public class Token implements Terminal {
	private String name;
	private Object value;
	
	public Token(String name, Object value) {
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

	public void setValue(Object value) {
		this.value = value;
	}
}