package com.springone.initializr.springoneinitializr.customizer;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.springone.initializr.springoneinitializr.controller.SpringOneProjectDescription;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

/**
 * @author Joachim Pasquali
 */
public class ProjectVariableContributor implements ProjectContributor {

	private static final String TEMPLATE_NAME = "project-vars";
	private static final String FILENAME = "project-vars.yml";
	private static final String CF_ORG = "cfOrg";
	private static final String CF_ROUTE_PREFIX = "cfRoutePrefix";

	private final MustacheTemplateRenderer renderer;
	private final SpringOneProjectDescription description;

	public ProjectVariableContributor(final SpringOneProjectDescription pDescription, final MustacheTemplateRenderer pTemplateRenderer) {
		this.description = pDescription;
		this.renderer = pTemplateRenderer;
	}

	@Override
	public void contribute(final Path pProjectRoot) throws IOException {
		final Path file = Files.createFile(pProjectRoot.resolve(FILENAME));
		final String cfRoutePrefix = UPPER_CAMEL.to(LOWER_HYPHEN, this.description.getArtifactId());

		final Map<String, String> projectVarMap = new HashMap<>();
		projectVarMap.put(CF_ROUTE_PREFIX, cfRoutePrefix);
		projectVarMap.put(CF_ORG, this.description.getCloudFoundryOrg());

		Files.write(file, this.renderer.render(TEMPLATE_NAME, projectVarMap).getBytes());
	}
}
