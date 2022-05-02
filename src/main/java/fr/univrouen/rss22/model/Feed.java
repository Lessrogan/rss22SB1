
package fr.univrouen.rss22.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Table
@Entity
@XmlRootElement(name = "feed", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.NONE)
public class Feed {
	
	@Id
	@GeneratedValue
	private int id;
	
	@XmlElement
	private String title;
	
	@XmlElement
	private String pubDate;
	
	@XmlElement(name = "item")
	@ManyToMany(targetEntity = Item.class)
	List<Item> items;
	
	public Feed() {
		items = new ArrayList<Item>();
	}
	
	public Feed(String t, String d) {
		items = new ArrayList<Item>();
		title = t;
		pubDate = d;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
}
