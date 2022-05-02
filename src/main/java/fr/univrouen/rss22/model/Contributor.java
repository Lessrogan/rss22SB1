package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contributor", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Contributor extends Person {

	public Contributor() {
		
	}
	
	public Contributor(String n, String e, String u) {
		super(n, e, u);
	}
	
}
