package fr.univrouen.rss22.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.persistence.sessions.serializers.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class RequestManager {

	private File file;
	
	public void setFile(File f) {
		file = f;
	}
	
	public boolean hasFile() {
		return file != null;
	}
	
	public File getFile() {
		return file;
	}
	
	
	// Retourne la réponse à la requête HTTP
	public String send(String urlString, String method) {
		try {
			//	Crée l'URL
			URL url = new URL(urlString);
			//	Crée la connexion HTTP
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//	Set la méthode
			con.setRequestMethod(method);
			// Récupère le statut de la réponse
			int status = con.getResponseCode();
			//	Récupère le contenu de réponse
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			String contentString = content.toString();
			return "Response from " + urlString + "\n" 
			+ (file != null ? "with " + file.getName() + "\n" : "")
			+ "Status: " + status + "\n"
			+ "Method: " + method + "\n"
			+ (contentString.startsWith("<xml") ? parse(contentString, ContentType.XML) : parse(contentString, ContentType.HTML));
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	private String parse(String xml, ContentType type) {
		String result = null;
		// A REVOIR (PROBLEME D'ACCENTS)
		xml = xml.replace("&eacute;", "é");
		System.out.println(xml);
		try {
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        format.setEncoding("UTF-8");

	        org.dom4j.Document document = DocumentHelper.parseText(xml);
	        StringWriter sw = new StringWriter();
	        XMLWriter writer = type.getNewWriter(sw, format);
	        writer.write(document);
	        result = sw.toString();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return result;
		
	}
	
}
