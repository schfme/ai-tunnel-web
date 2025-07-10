package me.schf.ai.tunnel.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import me.schf.ai.tunnel.web.service.AiHtmlGenerationService;

@ExtendWith(MockitoExtension.class)
class ArbitraryPathControllerTest {

	@Mock
	private AiHtmlGenerationService aiHtmlGenerationService;

	@Mock
	private Model model;

	@Mock
	private HttpServletRequest request;

	private ArbitraryPathController controller;

	@BeforeEach
	void setup() {
		controller = new ArbitraryPathController(aiHtmlGenerationService);
	}

	@Test
	void test_handleRootRedirect_addsRandomStartingPointsAndReturnsAbout() {
		String view = controller.handleRootRedirect(model);

		verify(model).addAttribute(eq("randomStartingPoints"), any(List.class));
		assertEquals("about", view);
	}

	@Test
	void test_handleDynamicPath_addsTitleAndHtmlContentAndReturnsNoCache() {
		String requestUri = "/some/path";
		String generatedHtml = "<html>Generated</html>";

		when(request.getRequestURI()).thenReturn(requestUri);
		when(aiHtmlGenerationService.generateHtmlFor(requestUri)).thenReturn(generatedHtml);

		String view = controller.handleDynamicPath(request, model);

		verify(request).getRequestURI();
		verify(model).addAttribute("title", "some/path");
		verify(model).addAttribute("htmlContent", generatedHtml);
		assertEquals("no-cache", view);
	}
}
