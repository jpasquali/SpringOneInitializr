package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.web.project.WebProjectRequest;

/**
 * @author Joachim Pasquali
 */
public class SpringOneWebProjectRequest extends WebProjectRequest {

	private String cloudFoundryOrg;

	public String getCloudFoundryOrg() {
		return this.cloudFoundryOrg;
	}

	public void setCloudFoundryOrg(final String pCloudFoundryOrg) {
		this.cloudFoundryOrg = pCloudFoundryOrg;
	}
}
