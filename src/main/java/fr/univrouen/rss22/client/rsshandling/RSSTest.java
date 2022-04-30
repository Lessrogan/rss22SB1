package fr.univrouen.rss22.client.rsshandling;

public class RSSTest {

	public static void main(String[] args) {
		XMLManager rssHandler = new XMLManager("src/main/resources/static/resume/xml/rss22.xml");
		rssHandler.read();
	}
	
}
