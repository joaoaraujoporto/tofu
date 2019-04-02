package io.tofu.commons.ts;

public class TSEntry {
	private String token; // is it needed?
	private String lexeme;
	private String type;
	private PositionInCode position;
	
	public TSEntry(String token, String lexeme, String type, PositionInCode position) {
		this.token = token;
		this.lexeme = lexeme;
		this.type = type;
		this.position = position;
	}

	public String getTokenName() {
		return token;
	}

	public String getLexeme() {
		return lexeme;
	}
	
	@Override
	public String toString() {
		return "tokenName: " + token +
				"lexeme: " + lexeme;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PositionInCode getPosition() {
		return position;
	}
}

