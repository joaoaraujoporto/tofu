package io.tofu.tepas;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.tofu.teprl.machines.Machine;

public abstract class MachineReader<T extends Machine> {
	public T read (File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        
        return read(doc);
	}

	protected abstract T read(Document doc);
}
