package me.schf.ai.tunnel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import me.schf.ai.tunnel.web.service.AiHtmlGenerationService;
import me.schf.ai.tunnel.web.util.Util;

@Controller
public class ArbitraryPathController {

	private final AiHtmlGenerationService aiHtmlGenerationService;

	public ArbitraryPathController(AiHtmlGenerationService aiHtmlGenerationService) {
		super();
		this.aiHtmlGenerationService = aiHtmlGenerationService;
	}

	@GetMapping({ "", "/" })
	public String handleRootRedirect(Model model) {
		model.addAttribute("randomStartingPoints", Util.randomStartingPoints());
		return "about";
	}

	@GetMapping("/**")
	public String handleDynamicPath(HttpServletRequest request, Model model) {
		String requestUri = request.getRequestURI();

		model.addAttribute("title", requestUri.substring(1));
		model.addAttribute("htmlContent", aiHtmlGenerationService.generateHtmlFor(requestUri));

		return "no-cache";
	}

}
