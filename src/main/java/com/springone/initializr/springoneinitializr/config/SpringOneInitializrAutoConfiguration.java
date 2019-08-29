package com.springone.initializr.springoneinitializr.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springone.initializr.springoneinitializr.controller.SpringOneProjectGenerationController;
import com.springone.initializr.springoneinitializr.controller.SpringOneProjectRequestToDescriptionConverter;

import com.springone.initializr.springoneinitializr.controller.SpringOneWebProjectRequest;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.InitializrProperties;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.support.DefaultInitializrMetadataProvider;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;

/**
 * @author Joachim Pasquali
 */
@Configuration
@EnableConfigurationProperties(SpringOneInitializrProperties.class)
public class SpringOneInitializrAutoConfiguration {

	@Bean
	public InitializrMetadataProvider customInitializrMetadataProvider(final InitializrProperties initializrProperties, final SpringOneInitializrProperties springOneInitializrProperties,
			final InitializrMetadataUpdateStrategy metadataUpdateStrategy) {
		final InitializrMetadata metaData = InitializrMetadataBuilder.fromInitializrProperties(springOneInitializrProperties.getInitializr()).withInitializrProperties(initializrProperties, true)
				.build();
		return new DefaultInitializrMetadataProvider(metaData, metadataUpdateStrategy);
	}

	@Bean
	public SpringOneProjectGenerationController projectGenerationController(InitializrMetadataProvider metadataProvider,
			ApplicationContext applicationContext) {
		ProjectGenerationInvoker<SpringOneWebProjectRequest> projectGenerationInvoker = new ProjectGenerationInvoker<>(
				applicationContext, new SpringOneProjectRequestToDescriptionConverter());
		return new SpringOneProjectGenerationController(metadataProvider, projectGenerationInvoker);
	}

}
