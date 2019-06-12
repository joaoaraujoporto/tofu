package io.tofu.teprl.readers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import io.tofu.teprl.machines.grammar.GLC;

public class GrammarReaderTest {

	@Test
	public void test() {
		GLC g;
		GrammarReader mr = new GrammarReader();
		File xmlFile = new File("src/test/java/resources/simple_grammar.xml");
		//File xmlFile = new File("src/test/java/resources/employees.xml");
				
		try {
			g = mr.read(xmlFile);
			System.out.println(g.getProductions().toString());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
