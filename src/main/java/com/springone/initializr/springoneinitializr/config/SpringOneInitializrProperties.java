package com.springone.initializr.springoneinitializr.config;

import io.spring.initializr.metadata.InitializrProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springone")
public class SpringOneInitializrProperties {

	private final InitializrProperties initializr = new InitializrProperties();

	public InitializrProperties getInitializr() {
		return initializr;
	}

}
