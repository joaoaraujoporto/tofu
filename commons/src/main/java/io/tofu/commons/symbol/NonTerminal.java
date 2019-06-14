package io.tofu.commons.symbol;

public class NonTerminal<Sign,Meaning> extends Symbol<Sign,Meaning> {
	
	public NonTerminal(Sign sign) {
		super(sign);
	}
	
	public NonTerminal(Sign sign, Meaning meaning) {
		super(sign, meaning);
	}

	public static <Sign,Meaning> NonTerminal<Sign,Meaning> getNonTerminal(Sign sign) {
		return new NonTerminal<Sign,Meaning>(sign);
	}
	
	public static <Sign,Meaning> NonTerminal<Sign,Meaning> getNonTerminal(Sign sign,
			Meaning meaning) {
		return new NonTerminal<Sign,Meaning>(sign, meaning);
	}
}
