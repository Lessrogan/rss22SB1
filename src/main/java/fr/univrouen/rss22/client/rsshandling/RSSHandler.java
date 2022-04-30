package fr.univrouen.rss22.client.rsshandling;

import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSHandler extends DefaultHandler {

	private int blankNb;
    private StringBuilder elementValue;
	
	public RSSHandler() {
		blankNb = 0;
	}
	
	@Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		for (int i = 0; i < blankNb; i++) {
			System.out.print("\t");
		}
		System.out.println("> " + localName);
		blankNb++;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		blankNb--;
		for (int i = 0; i < blankNb; i++) {
			System.out.print("\t");
		}
		System.out.println("< " + localName);
	}
	
}
