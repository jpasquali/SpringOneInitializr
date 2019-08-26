package com.springone.initializr.springoneinitializr.controller;

import io.spring.initializr.generator.project.MutableProjectDescription;

/**
 * @author Stephane Nicoll
 */
public class MutableSpringOneProjectDescription extends MutableProjectDescription
		implements SpringOneProjectDescription {

	private String cloudfoundryOrg;

	@Override
	public String getCloudfoundryOrg() {
		return this.cloudfoundryOrg;
	}

	public void setCloudfoundryOrg(String cloudfoundryOrg) {
		this.cloudfoundryOrg = cloudfoundryOrg;
	}

}
