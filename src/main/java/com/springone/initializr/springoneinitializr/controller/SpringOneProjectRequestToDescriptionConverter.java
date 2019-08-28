package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.web.project.DefaultProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;

/**
 * @author Stephane Nicoll
 */
public class SpringOneProjectRequestToDescriptionConverter implements ProjectRequestToDescriptionConverter {

	@Override
	public ProjectDescription convert(ProjectRequest request, InitializrMetadata metadata) {
		MutableSpringOneProjectDescription description = new MutableSpringOneProjectDescription();
		new DefaultProjectRequestToDescriptionConverter().convert(request, description, metadata);
		SpringOneWebProjectRequest springOneRequest = (SpringOneWebProjectRequest) request;
		description.setCloudfoundryOrg(springOneRequest.getCloudfoundryOrg());
		return description;
	}

}
