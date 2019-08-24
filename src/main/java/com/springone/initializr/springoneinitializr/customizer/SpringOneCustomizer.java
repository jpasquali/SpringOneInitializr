package com.springone.initializr.springoneinitializr.customizer;

import org.springframework.context.annotation.Bean;

import com.springone.initializr.springoneinitializr.config.SpringOneWebProjectRequest;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;

@ProjectGenerationConfiguration
public class SpringOneCustomizer {

	@Bean
	public ProjectVariableContributor pcfManifestContributor(final ResolvedProjectDescription pDescription, final MustacheTemplateRenderer pTemplateRenderer,
			final SpringOneWebProjectRequest pSpringOneWebProjectRequest) {
		return new ProjectVariableContributor(pDescription, pTemplateRenderer, pSpringOneWebProjectRequest);
	}
}
