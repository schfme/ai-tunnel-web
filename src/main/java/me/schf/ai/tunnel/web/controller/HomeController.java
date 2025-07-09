package me.schf.ai.tunnel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("")
	public String showAboutPage() {
		return "about";
	}
}
