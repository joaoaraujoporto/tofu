package io.tofu.commons.ts;

public class TSEntry {
	private String tokenName;
	private String lexeme;
	private String type;
	
	public TSEntry(String tokenName, String lexeme, String type) {
		this.tokenName = tokenName;
		this.lexeme = lexeme;
		this.setType(type);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

