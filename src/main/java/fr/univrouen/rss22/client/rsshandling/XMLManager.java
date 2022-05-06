package fr.univrouen.rss22.client.rsshandling;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
		try {
	Source input = new StreamSource(new StringReader(xml));
	SchemaFactory schemaFactory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	Source xsd = new StreamSource("file:src/main/resources/static/resume/xml/rss22.xsd");
	  Schema schema = schemaFactory.newSchema(xsd);
	  Validator validator = schema.newValidator();
	  validator.validate(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	
	public static String getRestrictedXMLFromFeed(Feed feed) throws JAXBException, XPathExpressionException, TransformerException, ParserConfigurationException, SAXException, IOException {
        return getRestrictedXML(getXMLFromFeed(feed));	
	}
	
	public static String getHTMLFromFeed(Feed feed) throws TransformerException, JAXBException, IOException {
		Source input = new StreamSource(new StringReader(getXMLFromFeed(feed)));
		Source xslt = new StreamSource("file:src/main/resources/static/resume/xml/rss22.xslt");
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult( writer );
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslt);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(input, result);
		return writer.getBuffer().toString();
	}
	
	public static void saveFeed(Feed feed, String fileName) throws JAXBException, IOException {
		String xml = getXMLFromFeed(feed);
		FileWriter fileWriter = new FileWriter(new File("src/main/resources/static/resume/xml/" + fileName));
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(xml);
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	
	public static String getRestrictedXML(String xml) throws XPathExpressionException, TransformerException, ParserConfigurationException, SAXException, IOException {
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(stream);
        
        NodeList itemNodes = document.getDocumentElement().getChildNodes();
        
        List<String> nodesToKeep = new ArrayList<String>(); {
        	nodesToKeep.add("p:guid");
        	nodesToKeep.add("p:title");
        	nodesToKeep.add("p:published");
        }
        for (int i = 0; i < itemNodes.getLength(); i++) {
            Node itemNode = itemNodes.item(i);
            NodeList itemAttributeNodes = itemNode.getChildNodes();
            for (int j = 0; j < itemAttributeNodes.getLength(); j++) {
                Node itemAttributeNode = itemAttributeNodes.item(j);
                if (itemAttributeNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (!nodesToKeep.contains(itemAttributeNode.getNodeName())) {
                    	itemNode.removeChild(itemAttributeNode);
                    }
                }
			}
        }
        return getStringFromXmlDocument(document);
	}
	
	public static String getStringFromXmlDocument(Document document) throws TransformerException {
		DOMSource source = new DOMSource(document);
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.transform(source, result);
	    return writer.toString();
	}
	
}
