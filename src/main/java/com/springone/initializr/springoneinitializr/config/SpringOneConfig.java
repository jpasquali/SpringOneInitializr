package com.springone.initializr.springoneinitializr.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.springone.initializr.springoneinitializr.controller.SpringOneController;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.metadata.DependencyMetadataProvider;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.InitializrProperties;
import io.spring.initializr.web.project.MainController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.SpringOneGenerationInvoker;
import io.spring.initializr.web.support.DefaultInitializrMetadataProvider;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;

@Configuration
@EnableConfigurationProperties(SpringOneInitializrProperties.class)
public class SpringOneConfig {

	@Bean
	public InitializrMetadataProvider customInitializrMetadataProvider(final InitializrProperties pInitializrProperties, final SpringOneInitializrProperties pSpringOneInitializrProperties,
			final InitializrMetadataUpdateStrategy pInitializrMetadataUpdateStrategy) {
		final InitializrMetadata metaData = InitializrMetadataBuilder.fromInitializrProperties(pSpringOneInitializrProperties.getInitializr()).withInitializrProperties(pInitializrProperties, true)
				.build();
		return new DefaultInitializrMetadataProvider(metaData, pInitializrMetadataUpdateStrategy);
	}

	@Bean
	public MainController mainController(final InitializrMetadataProvider pMetadataProvider, final TemplateRenderer pTemplateRenderer, final DependencyMetadataProvider pDependencyMetadataProvider,
			final ProjectGenerationInvoker pProjectGenerationInvoker) {
		return new SpringOneController(pMetadataProvider, pTemplateRenderer, pDependencyMetadataProvider, pProjectGenerationInvoker);
	}

	@Bean
	public ProjectGenerationInvoker projectGenerationInvoker(final GenericApplicationContext pParentApplicationContext, final ApplicationEventPublisher pEventPublisher,
			final ProjectRequestToDescriptionConverter pConverter) {
		return new SpringOneGenerationInvoker(pParentApplicationContext, pEventPublisher, pConverter);
	}

}
