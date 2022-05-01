package fr.univrouen.rss22.client.rsshandling;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
	
	public static String getHTMLFromFeed(Feed feed) throws TransformerException, JAXBException, IOException {
		saveFeed(feed, "rss22.tmp.xml");
		Source input = new StreamSource("file:src/main/resources/static/resume/xml/rss22.tmp.xml");
		Source xslt = new StreamSource("file:src/main/resources/static/resume/xml/rss22.xslt");
		String outputPath = "src/main/resources/static/resume/xml/rss22.html";
		Result output = new StreamResult(new File(outputPath)); 
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslt);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(input, output);
		return Files.readString(Paths.get(outputPath));
	}
	
	public static void saveFeed(Feed feed, String fileName) throws JAXBException, IOException {
		String xml = getXMLFromFeed(feed);
		System.out.println(xml);
		FileWriter fileWriter = new FileWriter(new File("src/main/resources/static/resume/xml/" + fileName));
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(xml);
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	
}
