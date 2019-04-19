package io.tofu.tepal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.PositionInCode;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.af.dt.DTState;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;

public class AL {
	private TS ts;
	private Alphabet alphabet;
	private LinkedList<Character> buffer;
	private BufferedReader bufferedReader;
	private int currentLine;
	private int currentColumn;
	private int lastLineSize;
	private char currentSymbol;
	private String currentLexeme;
	private ArrayList<DT> dts;
	private boolean bufferedReaderClosed;

	public AL(TS ts, Alphabet alphabet, ArrayList<DT> dts) {
		this.ts = ts;
		this.alphabet = alphabet;
		this.buffer = new LinkedList<Character>();
		this.dts = dts;
		
		currentLine = 1;
		currentColumn = 0;
		bufferedReaderClosed = false;
	}
	
	public ArrayList<Token> out(BufferedReader in) throws IOException {
		ArrayList<Token> tokens = new ArrayList<Token>();
		setBufferedReader(in);
		
		while (!bufferedReaderClosed)
			try {
				currentLexeme = new String();
				tokens.add(getNextToken());
			} catch (NotATokenException | UnsupportedSymbolException e) {
				System.err.println(e.getMessage());
			}
		
		return tokens;
	}
	
	public Token getNextToken() throws NotATokenException, IOException, UnsupportedSymbolException {
		ArrayList<DT> dtsReading = new ArrayList<DT>();
		ArrayList<DT> dtsNotReading = new ArrayList<DT>();
		
		for (DT dt : dts)
			try {
				dt.init();
			} catch (OperarMecanismoException e) {System.out.println(e.getMessage()); System.exit(0);}
		
		dtsReading.addAll(dts);
		
		
		while (!dtsReading.isEmpty()) {
			char c = popNextSymbol();
			
			currentLexeme += c;
			
			for (DT dt : dtsReading) {
				DTState s = null; // TODO - is that a good practice?
				
				try { s = dt.read(c); } catch (OperarMecanismoException e) {}
				
				if (s.isDead())
					dtsReading.remove(dt);
				
				if (!s.isAccept())
					continue;
					
				if (s.isBackable())
					back();
				
				int minimalLexemeSize = currentLexeme.length();
				
				if (!bufferedReaderClosed)
					try {
						return getNextToken();
					} catch (NotATokenException e) {
						for (int i = 0; i < currentLexeme.length() - minimalLexemeSize; i++)
							back();
					}
				
				return yieldToken(dt);
			}
		}
		
		throw new NotATokenException("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
				currentSymbol + "\"" + " something is wrong here.");
	}
	
	private Token yieldToken(DT dt) {
		String token = dt.getName();
		
		if (token == "ident") // TODO - as fast as possible, change this
			if (isKeyword(currentLexeme))
				return new Token(currentLexeme, null); // for now, no value is needed
		
		PositionInCode position = new PositionInCode(currentLine, currentColumn);
		ts.put(ts.size(), new TSEntry(token, currentLexeme, null, position));
		
		return new Token(token, ts.size());
	}

	private boolean isKeyword(String currentLexeme) {
		for (TSEntry tse : ts.values())
			if (tse.getToken().equals(currentLexeme))
				return true;
		
		return false;
	}

	public void back() {
		currentSymbol = currentLexeme.charAt(currentLexeme.length() - 1);
		currentLexeme = currentLexeme.substring(0, currentLexeme.length() - 1);
		currentColumn--;
		
		if (currentSymbol == '\n') {
			currentColumn = lastLineSize;
			currentLine--;
		}
		
		buffer.addFirst(currentSymbol);
	}
	
	private void setBufferedReader(BufferedReader bufferedReader) throws IOException {
		if (bufferedReader == null)
			throw new IOException("No bufferedReader to read."); // TODO - is it really a IOException?
		
		this.bufferedReader = bufferedReader;
	}
	
	public void setBuffer() throws IOException {
		int r = 0;
		
		for (int i = 0; i < 10 && r != -1; i++)
			if ((r = bufferedReader.read()) != -1)
				buffer.add(Character.valueOf((char) r));
		
		if (r == -1) {
			bufferedReader.close();
			bufferedReaderClosed = true;
		}
	}
	
	private Character popNextSymbol() throws IOException, UnsupportedSymbolException {
		currentColumn++;
		
		if (buffer.isEmpty())
			setBuffer();

		Character c = buffer.pop();
		
		if (c.equals(Character.valueOf('\n'))) {
			lastLineSize = currentColumn;
			currentColumn = 0;
			currentLine++;
		}
		
		currentSymbol = c;
		return c;
	}
	
	private Character getNextSymbol() throws IOException {		
		if (buffer.isEmpty())
			setBuffer();

		return buffer.getFirst();
	}
}