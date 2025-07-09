package me.schf.ai.tunnel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ArbitraryPathController {

	@GetMapping("/{path:.+}")
	public String handleDynamicPath(HttpServletRequest request, Model model) {
		String requestUri = request.getRequestURI().substring(1);

		model.addAttribute("title", requestUri);
		model.addAttribute("htmlContent", "generate me");

		return "no-cache.html";
	}

}
