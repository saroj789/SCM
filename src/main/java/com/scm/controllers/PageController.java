package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	
	@RequestMapping(path = "/")
	public String home(Model m) {
		m.addAttribute("name", "test");
		m.addAttribute("gitlink", "abc.html");
		return "home";
	}

	@RequestMapping(path = "/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping(path = "/service")
	public String service() {
		return "service";
	}

	@GetMapping(path = "/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping(path = "/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping(path = "/login")
	public String login() {
		return "login";
	}


}
