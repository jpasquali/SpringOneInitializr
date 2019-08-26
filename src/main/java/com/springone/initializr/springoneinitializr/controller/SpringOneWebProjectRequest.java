package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.web.project.WebProjectRequest;

public class SpringOneWebProjectRequest extends WebProjectRequest {

	private String cloudfoundryOrg;

	public String getCloudfoundryOrg() {
		return this.cloudfoundryOrg;
	}

	public void setCloudfoundryOrg(final String pCloundFoundryOrg) {
		this.cloudfoundryOrg = pCloundFoundryOrg;
	}
}
