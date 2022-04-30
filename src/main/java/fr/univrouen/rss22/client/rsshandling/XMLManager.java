package fr.univrouen.rss22.client.rsshandling;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


public class XMLManager {

	private String fileName;
	
	public XMLManager(String fn) {
		fileName = fn;
	}

	public void read() {
		String schemaFeature = "http://apache.org/xml/features/validation/schema";
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		try {
			factory.setFeature(schemaFeature, true);
		} catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		XMLReader reader = null;
		try {
			if (parser != null) {
				reader = parser.getXMLReader();
				reader.setContentHandler(new RSSHandler());
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
		try {
			if (reader != null) reader.parse(new InputSource(fileName));
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
		
	}
	
}
