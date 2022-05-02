package fr.univrouen.rss22.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RSSErrorController implements ErrorController {

//	@RequestMapping(value = "/error")
//	public @ResponseBody String getError() {
//		return "ERREUR ERREUR";
//	}
	
}
