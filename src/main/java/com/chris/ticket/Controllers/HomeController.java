package com.chris.ticket.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("name", "Chris");
		
		return "index";
	}
	
	@GetMapping("/start")
	public String start(Model model) {
		model.addAttribute("name", "Chris");
		
		return "index";
	}
	
}
