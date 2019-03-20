package io.tofu.tepal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.commons.symbol.Alfabeto;
import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.commons.*;

public class AL {
	private TS ts;
	private LinkedList<Character> buffer;
	private BufferedReader bufferedReader;
	private ArrayList<String> alfabeto;
	private int currentLine;
	private int currentColumn;

	public AL() {
		this.ts = new TS(); // TODO
		this.alfabeto = new Alfabeto();
		this.buffer = new LinkedList<Character>();
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
		
		currentLine = 1;
		currentColumn = 0;
	}
	
	public void setBuffer() throws Exception {
		if (bufferedReader == null)
			throw new Exception("No bufferedReader to read.");
				
		int r = 0;
		
		for (int i = 0; i < 10 && r != -1; i++) {
			r = bufferedReader.read();
			
			if (r != -1)
				buffer.add(Character.valueOf((char) r));
		}
		
		if (r == -1) {
			buffer.add(Character.valueOf('$'));
			bufferedReader.close();
		}
	}

	public Token getNextToken() throws Exception {
		try {
			return getNextToken(new String());
		} catch (NotATokenException e) {
			// TODO - tofu.setStackTrace(e.getMessage());
			buffer.removeFirst();
			return getNextToken();
		} catch (UnsupportedSymbolException e) {
			// TODO - tofu.setStackTrace(e.getMessage());
			buffer.removeFirst();
			return getNextToken();
		} catch (IOException e) {
			throw e;
		}
	}
	
	private Token getNextToken(String currentLexeme) throws Exception {
		Token token;
		currentLexeme += getNextCharacter().toString();
		
		if (endsWithWhitespace(currentLexeme))
			if (currentLexeme.length() == 1)
				return getNextToken(new String());
		
		if (currentLexeme.endsWith("$")) {
			if (currentLexeme.length() != 1) {
				if (!currentLexeme.substring(currentLexeme.length() - 1).equals(" "))
					buffer.addFirst(currentLexeme.charAt(currentLexeme.length() - 1));
				
				throw new NotATokenException("line " + currentLine + ": \"" +
						currentLexeme.substring(currentLexeme.length() - 1) + "\""
						+ " is not a supported token.");
			} else {
				return new Token("$", null);
			}
		}
		
		try {
			token = getToken(currentLexeme);
		} catch (NotATokenException | UnsupportedSymbolException e) {
			if (!endsWithWhitespace(currentLexeme)) {
				buffer.addFirst(currentLexeme.charAt(currentLexeme.length() - 1));
				currentColumn--;
			}
			
			throw e;
		}
		
		if (buffer.isEmpty())
			setBuffer();
		
		try {
			return getNextToken(currentLexeme);
		} catch (NotATokenException e) {
			if (token.getName().equals("id")) {
				ts.add(new TSEntry("id", currentLexeme));
				token.setValue(ts.size() - 1);
			}
			
			return token;
		} catch (UnsupportedSymbolException e) {
			throw e;
		}
	}

	private Token getToken(String lexeme) throws Exception {
		if (endsWithWhitespace(lexeme))
			throw new NotATokenException("Erro: line " + currentLine + ", column " + currentColumn + ": \"" +
					lexeme.substring(lexeme.length() - 1) + "\"" + " something is wrong here.");

		if (containsUnsupportedSymbol(lexeme))
			throw new UnsupportedSymbolException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
					lexeme.substring(lexeme.length() - 1) + "\"" + " is not a supported symbol.");
		
		Token token;
		
		if ((token = getNum(lexeme)) != null)
			return token;
		
		if ((token = getReal(lexeme)) != null)
			return token;
		
		if ((token = getBasic(lexeme)) != null)
			return token;
		
		if ((token = getRegularToken(lexeme)) != null)
			return token;
		
		if ((token = getKeyword(lexeme)) != null)
			return token;
		
		if ((token = getId(lexeme)) != null)
			return token;
		
		throw new NotATokenException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
				lexeme.substring(lexeme.length() - 1) + "\"" + " something is wrong here.");
	}

	private boolean endsWithWhitespace(String lexeme) {
		String end = lexeme.substring(lexeme.length() - 1);
		
		switch (end) {
			case " ": return true;
			case "	": return true;
			default: return false;
		}
	}

	private Token getRegularToken(String lexeme) throws Exception {
		switch (lexeme) {
			case "{": return new Token("{", null);
			case "}": return new Token("}", null);
			case "(": return new Token("(", null);
			case ")": return new Token(")", null);
			case ";": return new Token(";", null);
			case "=": return new Token("=", null);
			case "[": return new Token("[", null);
			case "]": return new Token("]", null);
			case "<": return new Token("<", null);
			case ">": return new Token(">", null);
			case "+": return new Token("+", null);
			case "-": return new Token("-", null);
			case "*": return new Token("*", null);
			case "/": return new Token("/", null);
			case "!": return new Token("!", null);
			case "|":
				lexeme += getNextCharacter().toString();
				
				if (lexeme.equals("||")) {
					return new Token("||", null);
				} else {
					buffer.addFirst(lexeme.charAt(lexeme.length() - 1));
					currentColumn--;
					return null;
				}
			case "&":
				lexeme += getNextCharacter().toString();
				
				if (lexeme.equals("&&")) {
					return new Token("&&", null);
				} else {
					buffer.addFirst(lexeme.charAt(lexeme.length() - 1));
					currentColumn--;
					return null;
				}
			case "==": return new Token("==", null);
			case "!=": return new Token("!=", null);
			case "<=": return new Token("<=", null);
			case ">=": return new Token(">=", null);
			case "true": return new Token("true", null);
			case "false": return new Token("false", null);
			default: return null;
		}
	}

	private Token getBasic(String lexeme) {
		switch (lexeme) {
			case "int": return new Token("basic", "int");
			case "real": return new Token("basic", "real");
			case "char": return new Token("basic", "char");
			case "void": return new Token("basic", "void");
			default: return null;
		}
	}

	private Token getReal(String lexeme) {
		if (lexeme.endsWith(".")) {
			for (int i = 0; i < lexeme.length() - 2; i++)
				if (!Character.isDigit(lexeme.charAt(i)))
					return null;
				
			return new Token("real", Double.valueOf(lexeme + "0"));
		}
		
		if (lexeme.startsWith(".")) {
			for (int i = 0; i < lexeme.length() - 2; i++)
				if (!Character.isDigit(lexeme.charAt(i)))
					return null;
				
			return new Token("real", Double.valueOf("0" + lexeme));
		}
		
		String[] parts = lexeme.split("\\.");
		
		if (parts.length != 2)
			return null;
		
		for (char c : parts[0].toCharArray())
			if (!Character.isDigit(c))
					return null;
		
		for (char c : parts[1].toCharArray())
			if (!Character.isDigit(c))
					return null;
		
		return new Token("real", Double.valueOf(lexeme));
	}

	private Token getNum(String lexeme) {
		for (char c : lexeme.toCharArray())
			if(!Character.isDigit(c))
				return null;
		
		return new Token("num", Integer.valueOf(lexeme));
	}

	private Token getId(String lexeme) {
		for (char c : lexeme.toCharArray())
			if(!Character.isDigit(c) && !Character.isLetter(c))
				return null;
		
		return new Token("id", null);
	}

	private Token getKeyword(String lexeme) {
		if (ts.containsKeyword(lexeme))
			return new Token(lexeme, null);
		
		return null;
	}

	private boolean containsUnsupportedSymbol(String lexeme) {
		for (char c : lexeme.toCharArray())
			if (!alfabeto.contains(Character.toString(c)))
				return true;
		
		return false;
	}
	
	private Character getNextCharacter() throws Exception {
		currentColumn++;
		
		if (buffer.isEmpty())
			setBuffer();

		Character c = buffer.pop();
		
		if (c.equals(Character.valueOf('\r')))
			return getNextCharacter();
		
		if (c.equals(Character.valueOf('\n'))) {
			currentColumn = 0;
			currentLine++;
			return getNextCharacter();
		}
		
		return c;
	}
}

