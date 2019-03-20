package io.tofu.commons.ts;

public class TSEntry {
	private String tokenName;
	private String lexeme;
	
	public TSEntry(String tokenName, String lexeme) {
		this.tokenName = tokenName;
		this.lexeme = lexeme;
	}

	public String getTokenName() {
		return tokenName;
	}

	public String getLexeme() {
		return lexeme;
	}
	
	@Override
	public String toString() {
		return "tokenName: " + tokenName +
				"lexeme: " + lexeme;
	}
}

