package io.tofu.tepas;

import io.tofu.commons.ts.TS;
import io.tofu.commons.symbol.EndOfSentence;
import io.tofu.commons.symbol.Token;
import io.tofu.tepal.Lexer;
import io.tofu.teprl.machines.grammar.GLC;

public class AS {
	private TS ts;
	private Lexer<Token<?>> lexer;
	private GLC<String,AttribSet> sdt;
	private TAS tas;
	private Recognizer recognizer;

	public AS(TS ts, Lexer<Token<?>> lexer, SDT<String,AttribSet> sdt) {
		this.ts = ts;
		this.lexer = lexer;
		this.sdt = sdt;
		this.tas = new TAS(sdt);
		this.recognizer = new Recognizer(ts, tas, sdt);
	}

	public void analyze() throws Exception {
		recognizer.clearStack();
		recognizer.addToStack(sdt.getStartSymbol());
		Token<?> token = lexer.getNextToken();
		
		while (!(token instanceof EndOfSentence)) {
			if (!token.getName().equals("white-space"))
				recognizer.input(token);
			
			token = lexer.getNextToken();
		}
		
		if (!recognizer.isStackEmpty())
			throw new SyntaxErrorException((Token<Integer>)token, ts);
	}
}