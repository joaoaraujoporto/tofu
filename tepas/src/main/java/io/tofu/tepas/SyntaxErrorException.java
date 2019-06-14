package io.tofu.tepas;

import io.tofu.commons.ts.PositionInCode;

public class SyntaxErrorException extends RuntimeException {
	
	SyntaxErrorException(PositionInCode positionInCode, String tokenName) {
		super("Error: line " + positionInCode.getLineNumber() + 
				", column " + positionInCode.getColNumberBegin() + 
				": \"" + tokenName  + "\"" + " syntax error.");
	}
	
	SyntaxErrorException(String tokenName) {
		super("Error: \"" + tokenName  + "\"" + " syntax error.");
	}
}
