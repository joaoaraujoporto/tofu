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
import io.tofu.tepas.AttribSet;
import io.tofu.tepas.GrammarReader;
import io.tofu.tepas.SDT;
import io.tofu.tepas.SDTReader;

public class Compiler {
	TS ts;
	AL al;
	SDT<String, AttribSet> sdt;
	AS as;
	
	public Compiler(File grammarFile) throws ParserConfigurationException, SAXException, IOException {
		ts = new TS();
		al = ALGenerator.getAL(ts);
		sdt = SDTReader.read(grammarFile, ts);
		as = new AS(ts, al, sdt);
	}

	public Collection<Token<Integer>> getTokens(BufferedReader bufferedReader) throws IOException {
		return al.out(bufferedReader);
	}
	
	public void parse(BufferedReader bufferedReader) throws Exception {
		al.setReadedTokens(bufferedReader);
		as.analyze();
	}
	
	public String compile(BufferedReader bufferedReader) throws Exception {
		parse(bufferedReader);
		return sdt.getStartSymbol().getMeaning().get("code").toString();
	}
	
}