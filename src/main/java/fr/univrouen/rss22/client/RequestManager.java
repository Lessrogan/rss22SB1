package fr.univrouen.rss22.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.persistence.sessions.serializers.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import fr.univrouen.rss22.logger.RSSLogger;


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
	public HTTPResponse send(String urlString, String method) {
		try {
			//	Crée l'URL
			URL url = new URL(urlString);
			//	Crée la connexion HTTP
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//	Set la méthode
			connection.setRequestMethod(method);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			if (method.equals("POST")) {
				connection.setRequestProperty("Accept", "application/xml");
				connection.setRequestProperty("Content-Type", "application/xml");
				OutputStream stream = connection.getOutputStream();
				OutputStreamWriter streamWriter = new OutputStreamWriter(stream);    
				List<String> lines = Files.readAllLines(Paths.get(getFile().getPath()));
				String body = String.join(System.lineSeparator(), lines);
				streamWriter.write(body);
				streamWriter.flush();
				streamWriter.close();
				stream.close();
			}
			
			// Récupère le statut de la réponse
			int status = connection.getResponseCode();
			//	Récupère le contenu de réponse
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			String contentString = content.toString();
			contentString = contentString.startsWith("<xml") ? parse(contentString, ContentType.XML) : parse(contentString, ContentType.HTML);
			HTTPResponse response = new HTTPResponse(status, contentString);
			return response;
		} catch (Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[0].getMethodName(), getClass().getName());
			e.printStackTrace();
			return null;
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
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[0].getMethodName(), getClass().getName());
	    	e.printStackTrace();
	    }
		return result;
		
	}
	
}
