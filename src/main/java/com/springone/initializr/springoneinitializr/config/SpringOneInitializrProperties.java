package com.springone.initializr.springoneinitializr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.spring.initializr.metadata.InitializrProperties;

/**
 * @author Joachim Pasquali
 */
@ConfigurationProperties("springone")
public class SpringOneInitializrProperties {

	private final InitializrProperties initializr = new InitializrProperties();

	public InitializrProperties getInitializr() {
		return this.initializr;
	}

}
