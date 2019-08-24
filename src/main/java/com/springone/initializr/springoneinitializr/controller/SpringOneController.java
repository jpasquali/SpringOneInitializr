package com.springone.initializr.springoneinitializr.controller;

import com.springone.initializr.springoneinitializr.config.SpringOneWebProjectRequest;

import java.util.Map;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.metadata.DependencyMetadataProvider;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.project.MainController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

public class SpringOneController extends MainController {

	public SpringOneController(InitializrMetadataProvider pMetadataProvider, TemplateRenderer pTemplateRenderer,
			DependencyMetadataProvider pDependencyMetadataProvider, ProjectGenerationInvoker pProjectGenerationInvoker) {
		super(pMetadataProvider, pTemplateRenderer, pDependencyMetadataProvider, pProjectGenerationInvoker);
	}

	@Override
	@ModelAttribute
	public ProjectRequest projectRequest(@RequestHeader Map<String, String> pHeaders) {
		SpringOneWebProjectRequest request = new SpringOneWebProjectRequest();
		request.getParameters().putAll(pHeaders);
		request.initialize(this.metadataProvider.get());
		return request;
	}
}
