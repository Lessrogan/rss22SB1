package fr.univrouen.rss22.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import fr.univrouen.rss22.client.rsshandling.XMLManager;
import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.ItemRepository;
import fr.univrouen.rss22.model.Person;
import fr.univrouen.rss22.model.PersonRepository;
import fr.univrouen.rss22.model.Author;
import fr.univrouen.rss22.model.Category;
import fr.univrouen.rss22.model.CategoryRepository;
import fr.univrouen.rss22.model.Feed;
import fr.univrouen.rss22.model.FeedRepository;

@Controller
public class DBController {
	
	@Autowired
	private FeedRepository feedRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	// A TRANSFORMER EN POST
	@GetMapping("/insert")
	public @ResponseBody String insert() throws JAXBException, TransformerException, IOException {
		Feed feed = XMLManager.getFeedFromFile("src/main/resources/static/resume/xml/rss22.xml");
		for (Item item : feed.getItems()) {
			if (item.getContributor() != null) {
				personRepository.save(item.getContributor());
			}
			if (item.getAuthor() != null) {
				personRepository.save(item.getAuthor());
			}
			if (item.getCategory() != null) {
				categoryRepository.save(item.getCategory());
			}
			itemRepository.save(item);
		}
		feedRepository.save(feed);
		return XMLManager.getHTMLFromFeed(feed);
	}
	
	@GetMapping(value = {"/resume/xml", "/resume/xml/{guid}"}, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String getXMLRSS(@PathVariable(name = "guid", required = false) Integer itemId) throws JAXBException, XPathExpressionException, TransformerException, ParserConfigurationException, SAXException, IOException {
		Feed feed = new Feed();
		if (itemId != null) {
			feed.addItem(itemRepository.findById(itemId).get());
		} else {
			Iterator<Item> items = itemRepository.findAll().iterator();
			while (items.hasNext()) {
				feed.addItem(items.next());
			}
		}
		return XMLManager.getRestrictedXMLFromFeed(feed);
	}

	@GetMapping(value = {"/resume/html", "/resume/html/{guid}"}) 
	public @ResponseBody String getHTMLRSS(@PathVariable(name = "guid", required = false) Integer itemId) throws JAXBException, TransformerException, IOException {
		Feed feed = new Feed();
		if (itemId != null) {
			feed.addItem(itemRepository.findById(itemId).get());
		} else {
			Iterator<Item> items = itemRepository.findAll().iterator();
			while (items.hasNext()) {
				feed.addItem(items.next());
			}
		}
		String xml = XMLManager.getHTMLFromFeed(feed);
		return xml;
	}
	
}
