package io.tofu.tepc_xpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.tepal.AL;
import io.tofu.tepas.AS;
import io.tofu.tepas.GrammarReader;

public class Compiler {
	TS ts;
	AL al;
	AS as;
	
	public Compiler(File grammarFile) throws ParserConfigurationException, SAXException, IOException {
		ts = new TS();
		al = ALGenerator.getAL(ts);
		as = new AS(ts, al, GrammarReader.read(grammarFile));
	}

	public Collection<Token<Integer>> getTokens(BufferedReader bufferedReader) throws IOException {
		return al.out(bufferedReader);
	}
	
	public void compile(BufferedReader bufferedReader) throws Exception {
		al.setReadedTokens(bufferedReader);
		as.analyze();
	}
}