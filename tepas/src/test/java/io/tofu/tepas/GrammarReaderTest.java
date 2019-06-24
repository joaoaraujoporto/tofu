package io.tofu.tepas;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Terminal;
import io.tofu.tepas.GrammarReader;
import io.tofu.teprl.machines.grammar.GLC;

public class GrammarReaderTest {

	@Test
	public void test() {
		GLC<String,AttribSet> g;
		//File xmlFile = new File("src/test/java/resources/simple_grammar.xml");
		File xmlFile = new File("../tepc-xpp/resources/xpp_grammar.xml");
		
		try {
			g = GrammarReader.read(xmlFile);
			if (g.getProductions() == null)
				System.out.println("SOmeth wornf");
			
			NonTerminal<String,AttribSet> nt = 
					new NonTerminal<String,AttribSet>("LVALUE");
			
			g.updateFirst();
			g.updateFollow(new Terminal<String,AttribSet>("endOfSentence"));
			System.out.println("Follow(" + nt.toString() + ") = " + g.getFollow(nt));
			System.out.println(g.getProductions());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
