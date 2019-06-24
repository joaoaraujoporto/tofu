package io.tofu.tepal;


public interface Lexer<Token> {
	public Token getNextToken();
}