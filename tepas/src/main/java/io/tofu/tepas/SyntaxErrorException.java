package io.tofu.tepas;

import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.PositionInCode;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;

public class SyntaxErrorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO - Improve syntax error message
	SyntaxErrorException(Token<Integer> token, TS ts) {
		super("line " + ts.get(token.getValue()).getPosition().getLineNumber() + 
				", column " + ts.get(token.getValue()).getPosition().getColNumberBegin() + 
				": \"" + ts.get(token.getValue()).getLexeme() + "\"" + " syntax error.");
	}
	
	SyntaxErrorException(Token<?> token) {
		super("\"" + token  + "\"" + " syntax error.");
	}
}
