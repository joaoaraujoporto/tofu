package io.tofu.teprl.readers;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import io.tofu.teprl.machines.grammar.Grammar;
import io.tofu.teprl.machines.grammar.NonTerminal;
import io.tofu.teprl.machines.grammar.Production;
import io.tofu.teprl.machines.grammar.Symbol;
import io.tofu.teprl.machines.grammar.Terminal;

public class GrammarReader extends MachineReader<Grammar>{
	
	@Override
	protected Grammar read(Document doc) {
		Grammar g = new Grammar("G");
		Node grammarNode = doc.getFirstChild();
		NodeList productionsList = grammarNode.getChildNodes();
		
		for (int i = 0; i < productionsList.getLength(); i++) {
			Node productionNode = productionsList.item(i);
			
			if (productionNode.getNodeType() != Node.)
				System.out.println("" + productionNode.getNodeType() + "," + Node.COMMENT_NODE);
			
			Node headNode = productionNode.getFirstChild();
			Node symbolNode = headNode.getFirstChild();
			NonTerminal head = new NonTerminal(symbolNode.getNodeValue());
			
			ArrayList<Symbol> body = new ArrayList<Symbol>();
			Node bodyNode = productionNode.getLastChild();
			NodeList symbolsOfBodyList = bodyNode.getChildNodes();
			
			for (int j = 0; j < symbolsOfBodyList.getLength(); j++) {
				symbolNode = symbolsOfBodyList.item(j);
				
				if (symbolNode.getNodeName().equals(GrammarTAGs.TERMINAL))
					body.add(new Terminal(symbolNode.getTextContent()));
				else if (symbolNode.getNodeName().equals(GrammarTAGs.NONTERMINAL))
					body.add(new NonTerminal(symbolNode.getTextContent()));
			}
			
			Production p = new Production(head, body);
			g.addProduction(p);
		}
		
		return g;
	}
}
