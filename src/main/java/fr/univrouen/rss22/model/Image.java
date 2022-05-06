package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Table
@Entity
@XmlRootElement(name = "img")
public class Image {

	@Id
	@GeneratedValue
	private int id;
	
	@XmlAttribute
	private String format;
	
	@XmlAttribute(name = "href")
	private String reference;
	
}
