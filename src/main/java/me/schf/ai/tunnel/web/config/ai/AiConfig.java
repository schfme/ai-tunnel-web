package me.schf.ai.tunnel.web.config.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.schf.ai.tunnel.web.config.aws.AwsConfig.ParameterRetriever;
import me.schf.ai.tunnel.web.config.parameter.ParameterNamesConfig;

@Configuration
public class AiConfig {

	@Bean(name = "htmlGenerateChatClient")
	ChatClient htmlGenerateChatClient(OpenAiChatModel openAiChatModel, String htmlGenerateSystemPrompt) {
		return ChatClient.builder(openAiChatModel).defaultSystem(htmlGenerateSystemPrompt).build();
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

	@Bean(name = "htmlGenerateSystemPrompt")
	String htmlGenerateSystemPrompt() {
		return """
				You are a skilled HTML generator. Your job is to generate only the contents that go inside <body> tags for a single-page HTML document.
				Use only inline CSS for styling. Based on the given path in the user prompt, return informative or entertaining content relevant to that path.
				The page must minimally include:

				- A title at the top.
				- One or two paragraphs of content related to the path with 3–5 links to other **relative subpaths** (e.g., if the path is /animals, link to /animals/dogs, not /dogs).
				- A "Back" link that uses history.back() for navigation but **never include links that go back or up in the URL path**. URLs should always grow larger or deeper, never shorter or shallower.
				- **Never move laterally.** For example, do not link from /animals/dogs to /animals/cats. Always link to subpaths, like /animals/dogs/facts or /animals/dogs/breeds.

				The style of the page should be creative, interesting, and visually match the tone or theme of the content
				(e.g., fun topics can use bright colors and playful fonts; serious topics should be styled clean and minimal).

				Do not include external scripts, stylesheets, or any links to external websites. Never include the full <html>, <head>, or <body> tags—only return the HTML that would go inside the <body>.

				Make the content visually readable using inline CSS (e.g. padding, font styling, link color). All links must use relative paths based on the current path context.
				For example:
				- If the current path is /animals, link to /animals/dogs or /animals/facts.
				- If the current path is /space/planets, link to /space/planets/mars or /space/planets/fun-facts.
				- Never link to / or /animals from /animals/dogs. URLs must only grow deeper.

				Return only HTML. No explanations.
				""";
	}

}