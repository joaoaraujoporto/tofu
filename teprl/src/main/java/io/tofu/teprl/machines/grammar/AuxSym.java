package io.tofu.teprl.machines.grammar;

import io.tofu.commons.symbol.Symbol;

public class AuxSym extends Symbol<String,String> {
	private static AuxSym or = new AuxSym("tofuAuxSymOr");
	private static AuxSym star = new AuxSym("tofuAuxSymStar");
	private static AuxSym plus = new AuxSym("tofuAuxSymPlus");
	private static AuxSym leftPar = new AuxSym("tofuAuxSymLeftParenthesis");
	private static AuxSym rightPar = new AuxSym("tofuAuxSymRightParenthesis");
	private static AuxSym possible = new AuxSym("tofuAuxSymPossible");
	
	public static AuxSym OR = or;
	public static AuxSym STAR = star;
	public static AuxSym PLUS = plus;
	public static AuxSym LEFTPAR = leftPar;
	public static AuxSym RIGHTPAR = rightPar;
	public static AuxSym POSSIBLE = possible;
	
	private AuxSym(String value) {
		super(value);
	}
	
	private static AuxSym getOr() {
		return new AuxSym("tofuAuxSymOr");
	}
	
	private static AuxSym getStar() {
		return new AuxSym("tofuAuxSymStar");
	}
	
	private static AuxSym getPlus() {
		return new AuxSym("tofuAuxSymPlus");
	}
	
	private static AuxSym getLeftParenthesis() {
		return new AuxSym("tofuAuxSymLeftParenthesis");
	}
	
	private static AuxSym getRightParenthesis() {
		return new AuxSym("tofuAuxSymRightParenthesis");
	}
}
