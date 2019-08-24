package com.springone.initializr.springoneinitializr.config;

import io.spring.initializr.web.project.WebProjectRequest;

public class SpringOneWebProjectRequest extends WebProjectRequest {

	private String cloundFoundryOrg;

	public String getCloundFoundryOrg() {
		return this.cloundFoundryOrg;
	}

	public void setCloundFoundryOrg(final String pCloundFoundryOrg) {
		this.cloundFoundryOrg = pCloundFoundryOrg;
	}
}
