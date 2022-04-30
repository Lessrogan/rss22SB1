package fr.univrouen.rss22.client.rsshandling;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.Feed;


public class XMLManager {

	public static Item getItemFromXML(String xml) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Item.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringWriter sw = new StringWriter();
        sw.append(xml);
        Item newArticle = (Item) unmarshaller.unmarshal(new StringReader(sw.toString()));
        return newArticle;
	}
	
	public static Feed getFeedFromXML(String xml) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Feed.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringWriter sw = new StringWriter();
        sw.append(xml);
        Feed feed = (Feed) unmarshaller.unmarshal(new StringReader(sw.toString()));
        return feed;
	}
	
	public static Item getItemFromFile(String fileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Item.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Item newArticle = (Item) unmarshaller.unmarshal(new File(fileName));
        return newArticle;
	}
	
	public static Feed getFeedFromFile(String fileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Feed.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Feed feed = (Feed) unmarshaller.unmarshal(new File(fileName));
        return feed;
	}
	
	public static String getXMLFromItem(Item article) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Item.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(article, sw);
        return sw.toString();		
	}
	
	public static String getXMLFromFeed(Feed feed) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Feed.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(feed, sw);
        return sw.toString();		
	}
	
}
