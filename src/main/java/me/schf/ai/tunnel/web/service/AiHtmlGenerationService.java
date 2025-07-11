package me.schf.ai.tunnel.web.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AiHtmlGenerationService {

	private final ChatClient htmlGenerateChatClient;

	public AiHtmlGenerationService(ChatClient htmlGenerateChatClient) {
		super();
		this.htmlGenerateChatClient = htmlGenerateChatClient;
	}

	@Async
	public CompletableFuture<String> generateHtmlFor(String uri) {
		String html = this.htmlGenerateChatClient
			.prompt()
			.user(uri)
			.call()
			.content();
		
		return CompletableFuture.completedFuture(html);
	}

}
