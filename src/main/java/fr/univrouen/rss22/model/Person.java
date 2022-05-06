package fr.univrouen.rss22.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Table
@Entity
public abstract class Person {

	@Id
	@GeneratedValue()
	private int id;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String email;
	
	@XmlElement()
	private String uri;
	
	public Person() {
		
	}
	
	public Person(String n, String e, String u) {
		name = n;
		email = e;
		uri = u;
	}
	
}
