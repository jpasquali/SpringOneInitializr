package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.generator.project.ProjectDescription;

/**
 * @author Stephane Nicoll
 */
public interface SpringOneProjectDescription extends ProjectDescription {

	String getCloudFoundryOrg();

}
