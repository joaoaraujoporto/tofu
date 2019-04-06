package io.tofu.commons.symbol;

import java.util.ArrayList;

public class Alphabet extends ArrayList<String> {
	public Alphabet() {}
	
	public Alphabet(ArrayList<String> symbols) {
		addAll(symbols);
	}
	
	public Alphabet(Alphabet symbols) {
		addAll(symbols);
	}
	
	public static Alphabet getDefaultSymbols() {
		ArrayList<String> defaultSymbols = new ArrayList<String>();
		
		defaultSymbols.addAll(getLetter());
		defaultSymbols.addAll(getDigit());
		defaultSymbols.addAll(getOpa());
		defaultSymbols.addAll(getWhiteSpace());
		
		for (char c = ';'; c <= '>'; c++)
			defaultSymbols.add(String.valueOf(c));
		
		for (char c = '('; c <= ')'; c++)
			defaultSymbols.add(String.valueOf(c));
		
		for (char c = '{'; c <= '}'; c++)
			defaultSymbols.add(String.valueOf(c));
		
		for (char c = '!'; c <= '\"'; c++)
			defaultSymbols.add(String.valueOf(c));
		
		for (char c : ",.[]".toCharArray())
			defaultSymbols.add(String.valueOf(c));
		
		return new Alphabet(defaultSymbols);
	}
	
	public static Alphabet getDigit() {
		ArrayList<String> digito = new ArrayList<String>();
		
		for (char c = '0'; c <= '9'; c++)
		    digito.add(String.valueOf(c));
		
		return new Alphabet(digito);
	}
	
	public static Alphabet getLetter() {
		Alphabet letter = getLowerCaseLetter();
		letter.addAll(getUpperCaseLetter());
		
		return new Alphabet(letter);
	}
	
	public static Alphabet getLowerCaseLetter() {
		ArrayList<String> letter = new ArrayList<String>();
		
		for (char c = 'a'; c <= 'z'; c++)
		    letter.add(String.valueOf(c));
				
		return new Alphabet(letter);
	}
	
	public static Alphabet getUpperCaseLetter() {
		ArrayList<String> letter = new ArrayList<String>();
		
		for (char c = 'A'; c <= 'Z'; c++)
		    letter.add(String.valueOf(c));
				
		return new Alphabet(letter);
	}
	
	public static Alphabet getOpa() {
		ArrayList<String> opa = new ArrayList<String>();
		
		opa.add("+");
		opa.add("-");
		opa.add("*");
		opa.add("/");
		opa.add("%");
		
		return new Alphabet(opa);
	}
	
	public static Alphabet getWhiteSpace() {
		ArrayList<String> whiteSpace = new ArrayList<String>();
		
		whiteSpace.add(" ");
		whiteSpace.add("\n");
		whiteSpace.add("\r");
		whiteSpace.add("\t");
				
		return new Alphabet(whiteSpace);
	}
}

