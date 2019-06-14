package io.tofu.commons.symbol;

public class Terminal<Sign,Meaning> extends Symbol<Sign,Meaning> {
	
	public Terminal(Sign sign) {
		super(sign);
	}
	
	public Terminal(Sign sign, Meaning meaning) {
		super(sign, meaning);
	}

	public static <Sign,Meaning> Terminal<Sign,Meaning> getTerminal(Sign sign) {
		return new Terminal<Sign,Meaning>(sign);
	}
	
	public static <Sign,Meaning> Terminal<Sign,Meaning> getTerminal(Sign sign,
			Meaning meaning) {
		return new Terminal<Sign,Meaning>(sign, meaning);
	}
	
	@Override
	public boolean equals(Object o) {
		Terminal<?,?> terminal = (Terminal<?,?>) o;
		
		if (sign.equals(terminal.getSign()))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return sign.hashCode();
	}
}
