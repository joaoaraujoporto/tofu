package io.tofu.tepas;

import io.tofu.commons.ts.TS;
import io.tofu.commons.symbol.Token;
import io.tofu.tepal.Lexer;
import io.tofu.teprl.machines.grammar.GLC;

public class AS {
	// private TS ts; - TODO define the role
	private Lexer<Token<?>> lexer;
	private GLC<String,AttribSet> glc;
	private TAS tas;
	private Recognizer recognizer;

	public AS(TS ts, Lexer<Token<?>> lexer, GLC<String,AttribSet> glc) {
		// this.ts = ts; - TODO
		this.lexer = lexer;
		this.glc = glc;
		this.tas = new TAS(glc);
		this.recognizer = new Recognizer(tas);
	}

	public void analyze() throws Exception {
		recognizer.clearStack();
		recognizer.addToStack(glc.getStartSymbol());
		Token<?> token = lexer.getNextToken();
		
		while (!(token instanceof EndOfSentence)) {
			recognizer.input(token);
			token = lexer.getNextToken();			
		}
	
		recognizer.input(token);
		
		if (!recognizer.isStackEmpty())
			throw new SyntaxErrorException(token.getName());
	}
}