package io.tofu.tepal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.commons.symbol.Token;
import io.tofu.commons.symbol.EndOfSentence;
import io.tofu.commons.ts.PositionInCode;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.commons.ts.TSEntryToken;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.af.dt.DTState;

public class AL implements Lexer<Token<?>> {
	private TS ts;
	private LinkedList<Character> buffer;
	private BufferedReader bufferedReader;
	private int currentLine;
	private int currentColumn;
	private int lastLineSize;
	private char currentSymbol;
	private String currentLexeme;
	private ArrayList<DT> dts;
	private boolean bufferedReaderClosed;
	private boolean stopReading;
	private ArrayList<Token<Integer>> readedTokens;
	private int nextTokenIndex;

	public AL(TS ts, ArrayList<DT> dts) {
		this.ts = ts;
		this.buffer = new LinkedList<Character>();
		this.dts = dts;
		
		currentLine = 1;
		currentColumn = 0;
		bufferedReaderClosed = false;
		stopReading = false;
	}
	
	public AL(TS ts, ArrayList<DT> dts, BufferedReader in) throws IOException {
		this(ts, dts);
		setReadedTokens(in);
	}
	
	public void setReadedTokens(BufferedReader in) throws IOException {
		readedTokens = out(in);
	}

	// TODO - Refactor to private and rename to readTokens
	public ArrayList<Token<Integer>> out(BufferedReader in) throws IOException {
		ArrayList<Token<Integer>> tokens = new ArrayList<Token<Integer>>();
		setBufferedReader(in);
		
		while (!stopReading) {			
			ArrayList<DT> dtsReading = new ArrayList<DT>();
			currentLexeme = new String();
			
			for (DT dt : dts)
				dt.init();
			
			dtsReading.addAll(dts);
			
			Token<Integer> token = getNextToken(dtsReading);
			
			if (token == null) {
				System.err.println("Error: line " + currentLine + ", column " + currentColumn + ": \"" +
						currentSymbol + "\"" + " something is wrong here.");
				continue;
			}
			
			tokens.add(token);
		}
		
		tokens.add(new EndOfSentence());
		return tokens;
	}
	
	public Token<Integer> getNextToken(ArrayList<DT> dtsReading) throws IOException {
		ArrayList<DT> dtsNotReading = new ArrayList<DT>();
		
		while (!dtsReading.isEmpty()) {
			DT dtAcceptor = null;
			char c = popNextSymbol();
			currentLexeme += c;	
				
			for (DT dt : dtsReading) {
				DTState s = null; // TODO - is that a good practice?
				
				s = dt.read(c);
				
				if (s.isDead())
					dtsNotReading.add(dt);
				
				if (!s.isAccept())
					continue;
					
				dtAcceptor = dt;
			}
			
			dtsReading.removeAll(dtsNotReading);
			
			if (dtAcceptor == null)
				continue;
			
			if (dtAcceptor.getCurrState().isBackable())
				back();
			
			int minimalLexemeSize = currentLexeme.length();
			Token<Integer> token = null;
			
			if (!stopReading)
				if ((token = getNextToken(dtsReading)) != null)
					return token;
			
			for (int i = 0; i < currentLexeme.length() - minimalLexemeSize; i++)
				back();
			
			return yieldToken(dtAcceptor);
		}
		
		return null;
	}
	
	private Token<Integer> yieldToken(DT dt) {
		String tokenName = dt.getName();
		PositionInCode position = new PositionInCode(currentLine, currentColumn);
		
		ts.put(ts.size(), new TSEntryToken(tokenName, currentLexeme, null, position));
		
		if (tokenName == "ident") // TODO - as fast as possible, change this
			if (isKeyword(currentLexeme))
				return new Token<Integer>(currentLexeme, ts.size()); // for now, no value is needed
		
		return new Token<Integer>(tokenName, ts.size());
	}

	private boolean isKeyword(String currentLexeme) {
		for (TSEntry tse : ts.values())
			if (tse instanceof TSEntryToken) {
				TSEntryToken tsek = (TSEntryToken) tse;
				
				if (tsek.getToken().equals(currentLexeme))
					return true;
			}
			
		
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
	
	private void setBuffer() throws IOException  {		
		int r = 0;
		
		for (int i = 0; i < 10 && r != -1; i++)
			if ((r = bufferedReader.read()) != -1)
				buffer.add(Character.valueOf((char) r));
		
		if (r == -1) {
			bufferedReader.close();
			bufferedReaderClosed = true;
			buffer.add('\u0003');
		}
	}
	
	private Character popNextSymbol() throws IOException {
		currentColumn++;
		
		if (buffer.isEmpty())
			setBuffer();

		Character c = buffer.pop();
		
		if (c.equals(Character.valueOf('\n'))) {
			lastLineSize = currentColumn;
			currentColumn = 0;
			currentLine++;
		}
		
		if (buffer.isEmpty() && bufferedReaderClosed)
			stopReading = true;
		
		currentSymbol = c;
		return c;
	}

	public Token<Integer> getNextToken() {
		if (readedTokens == null || readedTokens.isEmpty())
			throw new TokensNotReadedException();
		
		if (nextTokenIndex == readedTokens.size())
			nextTokenIndex = 0;
		
		return readedTokens.get(nextTokenIndex++);
	}
}