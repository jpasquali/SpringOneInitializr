package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.generator.project.MutableProjectDescription;

/**
 * @author Stephane Nicoll
 */
public class MutableSpringOneProjectDescription extends MutableProjectDescription implements SpringOneProjectDescription {

	private String cloudFoundryOrg;

	@Override
	public String getCloudFoundryOrg() {
		return this.cloudFoundryOrg;
	}

	public void setCloudFoundryOrg(final String cloudFoundryOrg) {
		this.cloudFoundryOrg = cloudFoundryOrg;
	}

}
