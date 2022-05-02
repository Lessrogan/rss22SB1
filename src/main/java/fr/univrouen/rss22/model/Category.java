package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement(name = "category")
public class Category {

	@Id
	@XmlAttribute
	private String term;
	
	public Category() {}
	
	public Category(String t) {
		term = t;
	}
	
}
