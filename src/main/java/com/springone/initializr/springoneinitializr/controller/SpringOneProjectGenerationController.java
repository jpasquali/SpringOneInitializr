package com.springone.initializr.springoneinitializr.controller;

import java.util.Map;

import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;

import org.springframework.web.bind.annotation.RequestHeader;

public class SpringOneProjectGenerationController extends ProjectGenerationController<SpringOneWebProjectRequest> {

	public SpringOneProjectGenerationController(InitializrMetadataProvider metadataProvider,
			ProjectGenerationInvoker projectGenerationInvoker) {
		super(metadataProvider, projectGenerationInvoker);
	}

	@Override
	public SpringOneWebProjectRequest projectRequest(@RequestHeader Map<String, String> headers) {
		SpringOneWebProjectRequest request = new SpringOneWebProjectRequest();
		request.getParameters().putAll(headers);
		request.initialize(getMetadata());
		return request;
	}
}
