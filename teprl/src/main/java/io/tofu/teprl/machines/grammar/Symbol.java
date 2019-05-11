package io.tofu.teprl.machines.grammar;

public class Symbol {
	String value;
	
	public Symbol(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}