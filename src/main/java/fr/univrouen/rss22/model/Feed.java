
package fr.univrouen.rss22.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;

@XmlRootElement(name = "feed", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.NONE)
public class Feed {
	@XmlElement(name = "item")
	List<Item> items;
	
	public Feed() {
		items = new ArrayList<Item>();
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
}
