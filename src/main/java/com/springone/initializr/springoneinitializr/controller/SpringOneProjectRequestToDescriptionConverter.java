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
	public ProjectDescription convert(final ProjectRequest request, final InitializrMetadata metadata) {
		final MutableSpringOneProjectDescription description = new MutableSpringOneProjectDescription();
		new DefaultProjectRequestToDescriptionConverter().convert(request, description, metadata);
		final SpringOneWebProjectRequest springOneRequest = (SpringOneWebProjectRequest) request;
		description.setCloudFoundryOrg(springOneRequest.getCloudFoundryOrg());
		return description;
	}

}
