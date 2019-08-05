package io.tofu.tepas;

import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntryToken;

public class SyntaxErrorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO - Improve syntax error message
	SyntaxErrorException(Token<Integer> token, TS ts) {
		super("line " + ((TSEntryToken) ts.get(token.getValue())).getPosition().getLineNumber() + 
				", column " + ((TSEntryToken) ts.get(token.getValue())).getPosition().getColNumberBegin() + 
				": \"" + ((TSEntryToken) ts.get(token.getValue() - 1)).getLexeme() + "\"" + " syntax error.");
	}
	
	SyntaxErrorException(Token<?> token) {
		super("\"" + token  + "\"" + " syntax error.");
	}
}
