package me.schf.ai.tunnel.web.config.parameter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.parameter-names")
public class ParameterNamesConfig {

	private String openAiApiKeyPath;

	public String getOpenAiApiKeyPath() {
		return openAiApiKeyPath;
	}

	public void setOpenAiApiKeyPath(String openAiApiKeyPath) {
		this.openAiApiKeyPath = openAiApiKeyPath;
	}

}