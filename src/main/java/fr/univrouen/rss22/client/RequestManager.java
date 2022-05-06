package fr.univrouen.rss22.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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
		//	Crée la connexion HTTP
		//	Crée l'URL
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
		} catch (Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			int status = 404;
			try {
				if (connection != null) {
					connection.getResponseCode();	
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			HTTPResponse response = new HTTPResponse(status, "");
			return response;
		}
		try {
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
			InputStream inputStream = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			String contentString = content.toString();
			if (contentString.startsWith("<?xml")) {
				contentString = parse(contentString, ContentType.XML);
			} else if (contentString.contains("<html>")) {
				contentString = parse(contentString, ContentType.XML);
			}
			HTTPResponse response = new HTTPResponse(status, contentString);
			return response;
		} catch (Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			int status = 404;
			try {
				if (connection != null) {
					status = connection.getResponseCode();	
				}
			} catch (IOException e1) {
				RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
			}
			HTTPResponse response = new HTTPResponse(status, "");
			return response;
		}
	}
	
	private String parse(String xml, ContentType type) {
		String result = null;
		xml = SpecialCharacter.replaceHTMLCharactersInString(xml);
		try {
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        format.setEncoding("UTF-8");
	        
	        org.dom4j.Document document = DocumentHelper.parseText(xml);
	        StringWriter sw = new StringWriter();
	        XMLWriter writer = type.getNewWriter(sw, format);
	        writer.write(document);
	        result = sw.toString();
	    } catch (Exception e) {
			RSSLogger.logError(e.getMessage(), Thread.currentThread().getStackTrace()[1].getMethodName(), getClass().getName());
	    	e.printStackTrace();
	    }
		return result;
		
	}
	
}
