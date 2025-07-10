package me.schf.ai.tunnel.web.controller;

import java.time.LocalDate;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import me.schf.ai.tunnel.web.service.AiHtmlGenerationService;
import me.schf.ai.tunnel.web.util.Util;

@Controller
public class ArbitraryPathController {

	private final AiHtmlGenerationService aiHtmlGenerationService;
	private final String currentYear;

	public ArbitraryPathController(AiHtmlGenerationService aiHtmlGenerationService) {
		super();
		this.aiHtmlGenerationService = aiHtmlGenerationService;
		this.currentYear = String.valueOf(LocalDate.now().getYear());
	}

	@GetMapping({ "", "/" })
	public String handleRootRedirect(Model model) {
		model.addAttribute("year", currentYear);
		model.addAttribute("randomStartingPoints", Util.randomStartingPoints());

		return "about";
	}

	@GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
	public String handleDynamicPath(HttpServletRequest request, Model model) {
		String requestUri = request.getRequestURI();
		
	    if (requestUri.matches(".*\\.(js|css|png|jpg|jpeg|ico|svg|woff|woff2|ttf|map)$")) {
	        return null; // let spring handle these.
	    }

		model.addAttribute("title", requestUri.substring(1));
		model.addAttribute("htmlContent", aiHtmlGenerationService.generateHtmlFor(requestUri));

		return "no-cache";
	}

}
