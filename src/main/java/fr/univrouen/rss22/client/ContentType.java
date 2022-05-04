package fr.univrouen.rss22.client;

import java.io.Writer;

import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public enum ContentType {

	HTML, XML;
	
	public XMLWriter getNewWriter(Writer w, OutputFormat f) {
		XMLWriter writer = null;
		switch (this) {
		case HTML:
			writer = new HTMLWriter(w, f);
			break;
		case XML:
			writer = new XMLWriter(w, f);
			break;
		
		}
		return writer;
	}
	
}
