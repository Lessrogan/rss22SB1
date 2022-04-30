package fr.univrouen.rss22.controllers;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.univrouen.rss22.client.rsshandling.XMLManager;
import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.ItemRepository;
import fr.univrouen.rss22.model.Feed;

@Controller
public class DBController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostMapping("/insert")
	public @ResponseBody String insert(@RequestParam String title) throws JAXBException {
		Item item = new Item(title);
		itemRepository.save(item);
		return "Saved!";
	}
	
	@GetMapping("/resume/xml") 
	public @ResponseBody String getXMLRSS() throws JAXBException {
		Feed feed = new Feed();
		Iterator<Item> items = itemRepository.findAll().iterator();
		while (items.hasNext()) {
			feed.addItem(items.next());
		}
		String xml = XMLManager.getXMLFromFeed(feed);
		
		return xml;
	}
	
	
}
