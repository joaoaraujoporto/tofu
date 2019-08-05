package io.tofu.tepas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import io.tofu.commons.ts.TS;
import io.tofu.tepas.procedures.AssignValue;
import io.tofu.tepas.procedures.Assignment;
import io.tofu.tepas.procedures.Procedure;
import io.tofu.tepas.procedures.StoreInTS;
import io.tofu.teprl.machines.grammar.Production;

public class SDTReader {
	
	public static SDT<String,AttribSet> read(Document doc, TS ts) {
		NodeList productionList = doc.getElementsByTagName(GrammarTAGs.PRODUCTION);
		SDT<String,AttribSet> sdt = null;
		
		for (int i = 0; i < productionList.getLength(); i++) {
			Node productionNode = productionList.item(i);
			Element productionElement = (Element) productionNode;
			NodeList headList = productionElement.getElementsByTagName(GrammarTAGs.HEAD);			
			Element headElement = (Element) headList.item(0);
			NodeList symbolList = headElement.getElementsByTagName(GrammarTAGs.NONTERMINAL);
			Node symbolNode = symbolList.item(0);
			
			NonTerminal<String,AttribSet> head = 
					new NonTerminal<String,AttribSet>(symbolNode.getTextContent(), new AttribSet());
			
			if (i == 0)
				sdt = new SDT<String,AttribSet>("SDT", head,
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
			sdt.addProduction(p);
			
			NodeList semanticList = productionElement.getElementsByTagName(GrammarTAGs.SEMANTICRULE);
			
			for (int j = 0; j < semanticList.getLength(); j++) {
				Node semanticNode = semanticList.item(i);
				Element semanticElement = (Element) semanticNode;
				NodeList nameList = semanticElement.getElementsByTagName(GrammarTAGs.NAME);			
				Node nameNode = nameList.item(0);
				NodeList typeList = semanticElement.getElementsByTagName(GrammarTAGs.TYPE);			
				Node typeNode = typeList.item(0);
				
				String nameProcedure = nameNode.getTextContent();
				String typeSemantic = typeNode.getTextContent();
				
				Procedure procedure;
				
				switch(nameProcedure) {
				case "assignment": procedure = new Assignment(); break;
				case "storeInTS": procedure = new StoreInTS(ts); break;
				case "assignValue": procedure = new AssignValue(ts); break;
				default: throw new RuntimeException("Error reading SDT in XML, no valid name to semantic-rule");
				}
				
				SemanticRule sr = new SemanticRule(procedure, typeSemantic);
				sdt.addSemanticRule(p, sr);
			}
		}
		
		return sdt;
	}
	
	public static SDT<String,AttribSet> read (File xmlFile, TS ts) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        
        return read(doc, ts);
	}
}
