package com.springone.initializr.springoneinitializr.customizer;

import org.springframework.context.annotation.Bean;

import com.springone.initializr.springoneinitializr.controller.SpringOneProjectDescription;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;

/**
 * @author Joachim Pasquali
 */
@ProjectGenerationConfiguration
public class SpringOneProjectGenerationConfiguration {

	@Bean
	public ProjectVariableContributor pcfManifestContributor(final SpringOneProjectDescription description, final MustacheTemplateRenderer templateRenderer) {
		return new ProjectVariableContributor(description, templateRenderer);
	}
}
