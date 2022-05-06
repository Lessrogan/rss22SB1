package fr.univrouen.rss22.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.SAXException;

import fr.univrouen.rss22.client.rsshandling.XMLManager;
import fr.univrouen.rss22.logger.RSSLogger;
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
public class RSSController {

	@Autowired
	private FeedRepository feedRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PostMapping("/insert")
	public @ResponseBody String insert(@RequestBody String xml, HttpServletResponse response) {
		try {
			System.out.println("[ XML ]");
			System.out.println(xml);
			Feed feed = XMLManager.getFeedFromXML(xml);
			//	Vérifie qu'un feed similaire n'existe pas
			List<Feed> feeds = (List<Feed>) feedRepository.findAll();
			for (Feed existingFeed : feeds) {
				if (existingFeed.getTitle().equals(feed.getTitle())
					&& existingFeed.getPubDate().equals(feed.getPubDate())) {
					throw new Exception("Feed already existing");
				}
			}
			
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
			response.setStatus(HttpServletResponse.SC_CREATED);
			List<Item> items = feed.getItems();
			return String.valueOf(items.get(items.size()-1).getGuid());
		} catch(Exception e) {
			e.printStackTrace();
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "";
		}
	}
	
	@DeleteMapping(value = {"/delete/{guid}"}, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String delete(@PathVariable(name = "guid") Integer itemId, HttpServletResponse response) {
		try {
			Item item  = itemRepository.findById(itemId).get();
			String xml = XMLManager.getXMLFromItem(item);
			List<Feed> feeds = (List<Feed>) feedRepository.findAll();
			for (Feed feed : feeds) {
				feed.removeItem(item);
				feedRepository.save(feed);
			}
			itemRepository.delete(item);
			return itemId.toString();
		} catch(Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "";
		}
	}
	
	@GetMapping(value = {"/resume/xml", "/resume/xml/{guid}"}, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String getXMLRSS(@PathVariable(name = "guid", required = false) Integer itemId, HttpServletResponse response) {
		try {
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
		} catch(Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "";
		}
	}

	@GetMapping(value = {"/resume/html", "/resume/html/{guid}"}) 
	public @ResponseBody String getHTMLRSS(@PathVariable(name = "guid", required = false) Integer itemId, HttpServletResponse response) {
		try {
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
		} catch(Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "";
		}
	}
	
}
