package io.tofu.teprl.machines.grammar;

public class Terminal extends Symbol {
	public static Terminal name = new Terminal(value);
	
	
	public Terminal(String value) {
		super(value);
	}

	public static Terminal getTerminal(String value) {
		return new Terminal(value);
	}
}
