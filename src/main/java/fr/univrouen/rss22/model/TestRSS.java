package fr.univrouen.rss22.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class TestRSS {
	
	private static Resource resource;
	
	public String loadFileXML() throws IOException{
		resource = new DefaultResourceLoader().
				getResource("classpath:xml/item.xml");
		InputStream fis = null;
		try {
			fis = resource.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder content = new StringBuilder();
		   try (Reader reader = new BufferedReader(new InputStreamReader
		     (fis, Charset.forName(StandardCharsets.UTF_8.name())))) {
		       int c = 0;
		       while ((c = reader.read()) != -1) {
		    	   content.append((char) c);
		       }
		   }
		
		return content.toString();
	}
	
	
}
