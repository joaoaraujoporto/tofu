package io.tofu.tepc_xpp;

import java.util.ArrayList;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
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
		setKeywords(ts);
		
		return new AL(ts, dts);
	}
	
	private static DT getDTIdent() {
		DT dt = new DT("ident");
		Alphabet digit = Alphabet.getDigit();
		Alphabet letter = Alphabet.getLetter();
		
		try {
			dt.addSymbols(digit);
			dt.addSymbols(letter);
			
			dt.createState("11", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("12", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("13", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("11", letter, "12");
			dt.addTransition("12", letter, "12");
			dt.addTransition("12", digit, "12");
			dt.addTransitionByOther("12", "13");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;		
	}
	
	private static DT getDTIntConstant() {
		DT dt = new DT("int-constant");
		Alphabet digit = Alphabet.getDigit();
		
		try {
			dt.addSymbols(digit);
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("2", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("0", digit, "1");
			dt.addTransition("1", digit, "1");
			dt.addTransitionByOther("1", "2");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTStringConstant() {
		DT dt = new DT("string-constant");
		
		try {
			dt.addSymbol("\"");
			
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("2", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("0", "\"", "1");
			dt.addTransitionByOther("1", "1");
			dt.addTransition("1", "\"", "2");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTOpr() {
		DT dt = new DT("opr");
		
		try {
			dt.addSymbol("<");
			dt.addSymbol(">");
			dt.addSymbol("=");
			dt.addSymbol("!");
			
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
			dt.addTransitionByOther("1", "3");
			dt.addTransition("0", ">", "4");
			dt.addTransition("4", "=", "5");
			dt.addTransitionByOther("4", "5");
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
			dt.addSymbols(opa);
			
			dt.createState("0", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("1", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("0", opa, "1");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static DT getDTWhiteSpace() {
		DT dt = new DT("white-space");
		Alphabet whiteSpace = Alphabet.getWhiteSpace();
		
		try {
			dt.addSymbols(whiteSpace);
			
			dt.createState("19", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("20", !DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("21", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, DTConstants.BACKABLE);
			
			dt.addTransition("19", whiteSpace, "20");
			dt.addTransition("20", whiteSpace, "20");
			dt.addTransitionByOther("20", "21");
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
				dt.addSymbol(punc);
				
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
			dt.addSymbol("=");
			
			dt.createState("22", DTConstants.START, !DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			dt.createState("23", !DTConstants.START, DTConstants.ACCEPT, !DTConstants.DEAD, !DTConstants.BACKABLE);
			
			dt.addTransition("22", "=", "23");
		} catch (EditarMecanismoException e) { e.printStackTrace(); }
		
		return dt;
	}
	
	private static void setKeywords(TS ts) {
		String[] keywords = new String[15];
		
		keywords[0] = "class";
		keywords[1] = "extends";
		keywords[2] = "int";
		keywords[3] = "string";
		keywords[4] = "constructor";
		keywords[5] = "break";
		keywords[6] = "print";
		keywords[7] = "read";
		keywords[8] = "return";
		keywords[9] = "super";
		keywords[10] = "if";
		keywords[11] = "else";
		keywords[12] = "for";
		keywords[13] = "new";
		keywords[14] = "null";
		
		for (String k : keywords)
			ts.put(ts.size(), new TSEntry(k, k, null, null));
	}
}