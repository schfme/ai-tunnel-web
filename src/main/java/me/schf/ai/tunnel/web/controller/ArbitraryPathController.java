package me.schf.ai.tunnel.web.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import me.schf.ai.tunnel.web.service.AiHtmlGenerationService;
import me.schf.ai.tunnel.web.util.HtmlValidator;
import me.schf.ai.tunnel.web.util.RandomWordGenerator;

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
		model.addAttribute("randomStartingPoints", RandomWordGenerator.randomStartingPoints());

		return "about";
	}

	/*
	 * {path:^(?!.*\\.).*$} matches a path segment with no dots (no file
	 * extensions). The trailing /** allows any nested sub-paths after that segment.
	 * So URLs like /minecraft/blocks but requests for static assets like /image.png
	 * do not.
	 */
	@GetMapping(value = "/{path:^(?!.*\\.).*$}/**") 
	public String handleDynamicPath(HttpServletRequest request, Model model) {
		String requestUri = request.getRequestURI();
			    
	    String aiGeneratedHtml = aiHtmlGenerationService.generateHtmlFor(requestUri);
	    
	    // make sure the robot is behaving.
	    boolean isValidHtml = new HtmlValidator(aiGeneratedHtml)
	    		.doesNotContainExternalLinks()
	    		.isValid();
	    
	    if (!isValidHtml) {
	    	throw new IllegalStateException("AI-generated HTML was no good: %s".formatted(aiGeneratedHtml));
	    }

		model.addAttribute("title", requestUri.substring(1));
		model.addAttribute("htmlContent", aiGeneratedHtml);

		return "no-cache";
	}

}
