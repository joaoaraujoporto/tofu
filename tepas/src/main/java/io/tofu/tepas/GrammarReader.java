package io.tofu.tepas;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import io.tofu.teprl.machines.grammar.GLC;
import io.tofu.teprl.machines.grammar.Production;

public class GrammarReader {
	
	protected GLC<String,AttribSet> read(Document doc) {
		NodeList productionList = doc.getElementsByTagName(GrammarTAGs.PRODUCTION);
		GLC<String,AttribSet> g = null;
		
		for (int i = 0; i < productionList.getLength(); i++) {
			Node productionNode = productionList.item(i);
			Element productionElement = (Element) productionNode;
			NodeList headList = productionElement.getElementsByTagName(GrammarTAGs.HEAD);			
			Element headElement = (Element) headList.item(0);
			NodeList symbolList = headElement.getElementsByTagName(GrammarTAGs.NONTERMINAL);
			Node symbolNode = symbolList.item(0);
			
			NonTerminal<String,AttribSet> head = 
					new NonTerminal<String,AttribSet>(symbolNode.getTextContent());
			
			if (i == 0)
				g = new GLC<String,AttribSet>("G", head,
						new Terminal<String,AttribSet>("epsilon"));
			
			ArrayList<Symbol<String,AttribSet>> body = 
					new ArrayList<Symbol<String,AttribSet>>();
			
			NodeList bodyList = productionElement.getElementsByTagName(GrammarTAGs.BODY);			
			Element bodyElement = (Element) bodyList.item(0);
			symbolList = bodyElement.getChildNodes();
			
			for (int j = 0; j < symbolList.getLength(); j++) {
				symbolNode = symbolList.item(j);
				
				if (symbolNode.getNodeName().equals(GrammarTAGs.TERMINAL))
					body.add(new Terminal<String,AttribSet>(symbolNode.getTextContent()));
				else if (symbolNode.getNodeName().equals(GrammarTAGs.NONTERMINAL))
					body.add(new NonTerminal<String,AttribSet>(symbolNode.getTextContent()));
			}
			
			Production<String,AttribSet> p = 
					new Production<String,AttribSet>(head, body);
			g.addProduction(p);
		}
		
		return g;
	}
}
