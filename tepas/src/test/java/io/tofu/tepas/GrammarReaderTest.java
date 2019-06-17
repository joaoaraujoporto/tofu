package io.tofu.tepas;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.tofu.tepas.GrammarReader;
import io.tofu.teprl.machines.grammar.GLC;

public class GrammarReaderTest {

	@Test
	public void test() {
		GLC<String,AttribSet> g;
		GrammarReader mr = new GrammarReader();
		File xmlFile = new File("src/test/java/resources/simple_grammar.xml");
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
			g = mr.read(doc);
			if (g.getProductions() == null)
				System.out.println("SOmeth wornf");
			
			System.out.println(g.getProductions());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
