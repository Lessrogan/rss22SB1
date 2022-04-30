package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "item", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.FIELD)
@Table
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@XmlElement(name = "guid")
	private int id;
	
	@XmlElement
	public String title;
	
	public Item() {
		
	}
	
	public Item(String t) {
		title = t;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	
}
