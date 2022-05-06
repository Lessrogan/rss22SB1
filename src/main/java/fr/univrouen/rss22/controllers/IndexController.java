package fr.univrouen.rss22.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
		
	@GetMapping("/")
	public ModelAndView accueil() {
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/accueil.html");
        return modelAndView;
	}
	
	@GetMapping("/help")
	public ModelAndView help() {
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/help.html");
        return modelAndView;
	}
	
}
