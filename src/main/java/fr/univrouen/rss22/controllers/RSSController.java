package fr.univrouen.rss22.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.univrouen.rss22.model.ItemBackup;

@RestController
public class RSSController {

	@GetMapping("/guid")
	public String getRSSinXML(
		@RequestParam(value = "guid") String texte) {
		return ("Détail du flux RSS demandé : " + texte);
	}
	
	@GetMapping("/test")
	public String getTestinXML(
		@RequestParam(value = "nb") String nombre,
		@RequestParam(value = "search") String texte) {
		return ("Test:<br>"
				+ "guid = " + nombre + "<br>"
				+ "titre = " + texte);
	}
	
	@RequestMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ItemBackup getXML() {
		ItemBackup it = new ItemBackup("12345678","Test item","2022-05-01T11:22:33");
		return it;
	}
}