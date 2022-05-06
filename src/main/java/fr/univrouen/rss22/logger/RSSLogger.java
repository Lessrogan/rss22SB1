package fr.univrouen.rss22.logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RSSLogger {

    private static final Logger LOGGER = Logger.getLogger("RSS22");
	
    public static void logError(String msg, String methodName, String errorClassName) {
    	
    	if (LOGGER.getHandlers().length == 0) {
			try {
	    		if (!Files.exists(Paths.get("logs"))) {
	                Files.createDirectory(Paths.get("logs"));
	    		}
	    		FileHandler fileHandler = new FileHandler("logs/rss22.log");
	            SimpleFormatter formatter = new SimpleFormatter();  
	            fileHandler.setFormatter(formatter);  
	    		LOGGER.addHandler(fileHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}	
    	}
    	
		LOGGER.logp(Level.SEVERE, errorClassName, methodName,msg);

    }
}
