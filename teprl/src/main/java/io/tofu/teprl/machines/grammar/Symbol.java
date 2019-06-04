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
	
	@Override
	public boolean equals(Object o) {
		Symbol sym = (Symbol) o;
		
		if (hashCode() == sym.hashCode())
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
}