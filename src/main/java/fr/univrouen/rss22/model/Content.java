package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Table
@Entity
@XmlRootElement(name = "content")
public class Content {

	@Id
	@GeneratedValue
	private int id;

	@XmlAttribute(name = "src", required = false)
	private String source;
	
    @XmlValue
    private String content;
	
}
