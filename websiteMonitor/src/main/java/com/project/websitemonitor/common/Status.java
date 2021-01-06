package com.project.websitemonitor.common;

public enum Status {

	UP("UP"), DOWN("DOWN"), WRONG_DOMAIN("WRONG DOMAIN");

	private String statusValue;

	Status(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatus() {
		return this.statusValue;
	}
}
