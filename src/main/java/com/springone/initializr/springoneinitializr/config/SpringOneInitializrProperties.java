package com.springone.initializr.springoneinitializr.config;

import io.spring.initializr.metadata.InitializrProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springone")
public class SpringOneInitializrProperties {

	private InitializrProperties initializr = new InitializrProperties();

	public InitializrProperties getInitializr() {
		return initializr;
	}

	public void setInitializr(InitializrProperties initializr) {
		this.initializr = initializr;
	}

}
