package fr.univrouen.rss22.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.TestRSS;

@RestController
public class POSTController {

	@RequestMapping(value = "/testpost", method = RequestMethod.POST,
	consumes = "application/xml")
	public String postTest(@RequestBody String flux) {
		return ("<result><response>Message reçu : </response>"  
			+ flux + "</result>");
	}
	
	@PostMapping(value = "/postrss", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String postRSS() throws IOException {
		TestRSS rss = new TestRSS();
		return rss.loadFileXML();
	}
}

