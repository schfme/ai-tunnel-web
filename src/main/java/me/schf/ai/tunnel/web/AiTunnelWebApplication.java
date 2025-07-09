package me.schf.ai.tunnel.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AiTunnelWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiTunnelWebApplication.class, args);
	}

}
