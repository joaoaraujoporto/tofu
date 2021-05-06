package io.tofu.tepc_xpp;

import java.util.ArrayList;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.ts.TS;
import io.tofu.tepal.AL;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.af.dt.DTConstants;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class ALGenerator {
	public static AL getAL(TS ts) {
		ArrayList<DT> dts = new ArrayList<DT>();
		
		dts.add(getDTIdent());
		dts.add(getDTIntConstant());
		dts.add(getDTStringConstant());
		dts.add(getDTOpr());
		dts.add(getDTOpa());
		dts.add(getDTWhiteSpace());
		dts.add(getDTAssign());
		dts.addAll(getDTsPunctuation());
		
		return new AL(ts, Alphabet.getDefaultSymbols(), dts);
	}
	
	private static DT getDTIdent() {
		DT dt = new DT("ident");
		Alphabet other = Alphabet.getDefaultSymbols();
		Alphabet digit = Alphabet.getDigit();
		Alphabet letter = Alphabet.getLetter();
		
		other.removeAll(digit);
		other.removeAll(letter);
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("11", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("12", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("13", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("11", letter, "12");
			dt.addTransition("12", letter, "12");
			dt.addTransition("12", digit, "12");
			dt.addTransition("12", other, "13");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;		
	}
	
	private static DT getDTIntConstant() {
		DT dt = new DT("int-constant");
		Alphabet other = Alphabet.getDefaultSymbols();
		Alphabet digit = Alphabet.getDigit();
		
		other.removeAll(digit);
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("2", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("0", digit, "1");
			dt.addTransition("1", digit, "1");
			dt.addTransition("1", other, "2");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTStringConstant() {
		DT dt = new DT("string-constant");
		Alphabet symbols = Alphabet.getDefaultSymbols();
		
		symbols.remove("\"");
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("2", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("0", "\"", "1");
			dt.addTransition("1", symbols, "1");
			dt.addTransition("1", "\"", "2");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTOpr() {
		DT dt = new DT("opr");
		Alphabet other = Alphabet.getDefaultSymbols();
		
		other.remove("=");
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("2", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("3", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			dt.createState("4", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("5", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("6", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			dt.createState("7", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("8", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("9", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("10", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("0", "<", "1");
			dt.addTransition("1", "=", "2");
			dt.addTransition("1", other, "3");
			dt.addTransition("0", ">", "4");
			dt.addTransition("4", "=", "5");
			dt.addTransition("4", other, "5");
			dt.addTransition("0", "=", "7");
			dt.addTransition("7", "=", "8");
			dt.addTransition("0", "!", "9");
			dt.addTransition("9", "=", "10");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTOpa() {
		DT dt = new DT("opa");
		Alphabet opa = Alphabet.getOpa();
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("0", opa, "1");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTWhiteSpace() {
		DT dt = new DT("white-space");
		Alphabet other = Alphabet.getDefaultSymbols();
		Alphabet whiteSpace = Alphabet.getWhiteSpace();
		
		other.removeAll(whiteSpace);
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("19", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("20", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("21", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("19", whiteSpace, "20");
			dt.addTransition("20", whiteSpace, "20");
			dt.addTransition("20", other, "21");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static ArrayList<DT> getDTsPunctuation() {
		ArrayList<DT> dts = new ArrayList<DT>();
		Alphabet punctuation = new Alphabet();
		
		punctuation.add("{");
		punctuation.add(";");
		punctuation.add("}");
		punctuation.add("[");
		punctuation.add("]");
		punctuation.add(",");
		punctuation.add(")");
		punctuation.add("(");
		punctuation.add(".");
		
		for (String punc : punctuation) {
			DT dt = new DT(punc);
			
			try {
				dt.addSymbols(Alphabet.getDefaultSymbols());
				
				dt.createState("22", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
				dt.createState("23", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
				
				dt.addTransition("22", punc, "23");
			} catch (EditarMecanismoException e) { e.printStackTrace(); }
			
			dts.add(dt);
		}
		
		return dts;
	}
	
	private static DT getDTAssign() {
		DT dt = new DT("assign");
		
		try {
			dt.addSymbols(Alphabet.getDefaultSymbols());
			
			dt.createState("22", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("23", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("22", "=", "23");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
}
