package me.schf.ai.tunnel.web.controller;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

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
	@GetMapping(value = "/{path:^(?!error$)(?!.*\\.).*$}/**")
	public CompletableFuture<ModelAndView> handleDynamicPath(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		
		System.out.println("calling for :" + requestUri);

		return aiHtmlGenerationService.generateHtmlFor(requestUri).thenApply(aiGeneratedHtml -> {
			boolean isValidHtml = new HtmlValidator(aiGeneratedHtml)
				.doesNotContainExternalLinks()
				.isValid();

			if (!isValidHtml) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "AI-generated HTML was invalid");
			}

			ModelAndView mav = new ModelAndView("no-cache");
			mav.addObject("title", requestUri.substring(1));
			mav.addObject("htmlContent", aiGeneratedHtml);
			return mav;
		});
	}
}
