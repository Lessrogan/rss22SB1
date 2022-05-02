package fr.univrouen.rss22.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlReadOnly;
import org.eclipse.persistence.oxm.annotations.XmlWriteOnly;

import java.time.LocalDateTime;

@XmlRootElement(name = "item", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.NONE)
@Table
@Entity
public class Item {

	@Id
	@XmlElement
	private int guid;
	
	@XmlElement
	private String title;
	
	@XmlElement
	private String published;
	
	@XmlElement
	@XmlReadOnly
	@ManyToOne(targetEntity = Category.class)
	private Category category;

	@XmlElement
	@ManyToOne(targetEntity = Person.class)
	private Author author;

	@XmlElement
	@ManyToOne(targetEntity = Person.class)
	private Contributor contributor;

	@XmlElement
	private String content;
	
	public Item() {}
	
	public Item(int g, String t, String d, Category c, Author a, String cnt) {
		guid = g;
		title = t;
		published = d;
		category = c;
		author = a;
		content = cnt;
	}
	
	public Item(int g, String t, String d, Category c, Contributor cn, String cnt) {
		guid = g;
		title = t;
		published = d;
		category = c;
		contributor = cn;
		content = cnt;
	}
	
	public int getGuid() {
		return guid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return published;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public Contributor getContributor() {
		return contributor;
	}
	
}
