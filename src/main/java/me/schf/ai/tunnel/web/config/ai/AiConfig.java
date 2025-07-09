package me.schf.ai.tunnel.web.config.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Bean(name = "commandRequestChatClient")
	ChatClient commandRequestChatClient(OpenAiChatModel openAiChatModel, String commandRequestSystemPrompt) {
		return ChatClient.builder(openAiChatModel).defaultSystem(commandRequestSystemPrompt).build();
	}

	@Bean(name = "openAiChatModel")
	OpenAiChatModel openAiChatModel(OpenAiApi openAiApi, OpenAiChatOptions openAiChatOptions) {
		return OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(openAiChatOptions).build();
	}

	@Bean(name = "chatModel")
	@ConfigurationProperties(prefix = "openai.chat")
	ChatModel chatModel() {
		return ChatModel.GPT_4_O_MINI;
	}

	@Bean(name = "openAiChatOptions")
	OpenAiChatOptions openAiChatOptions(ChatModel openAiChatModel) {
		return OpenAiChatOptions.builder().model(openAiChatModel).build();
	}

	@Bean(name = "openAiApi")
	OpenAiApi openAiApi(ParameterRetriever awsParameterRetriever, ParameterNamesConfig parameterNamesConfig) {
		return new OpenAiApi.Builder()
				.apiKey(awsParameterRetriever.getParameter(parameterNamesConfig.getOpenAiApiKeyPath())).build();
	}

	@Bean(name = "commandRequestSystemPrompt")
	String commandRequestSystemPrompt() {
		return "";
	}

}