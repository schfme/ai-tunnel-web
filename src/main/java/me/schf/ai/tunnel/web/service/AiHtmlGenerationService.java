package me.schf.ai.tunnel.web.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class AiHtmlGenerationService {

	private final ChatClient htmlGenerateChatClient;

	public AiHtmlGenerationService(ChatClient htmlGenerateChatClient) {
		super();
		this.htmlGenerateChatClient = htmlGenerateChatClient;
	}

	public String generateHtmlFor(String uri) {
		return this.htmlGenerateChatClient.prompt().user(uri).call().content();
	}

}
