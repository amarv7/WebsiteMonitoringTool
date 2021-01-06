package com.project.websitemonitor.model;

public class CheckDto {

	private int id;
	private String name;
	private String websiteUrl;
	private int frequency;

	public CheckDto() {
	}

	public CheckDto(String name, String websiteUrl, int frequency) {
		this.name = name;
		this.websiteUrl = websiteUrl;
		this.frequency = frequency;
	}

	public CheckDto(int id, String name, String websiteUrl, int frequency) {
		this.id = id;
		this.name = name;
		this.websiteUrl = websiteUrl;
		this.frequency = frequency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "CheckDto [id=" + id + ", name=" + name + ", websiteUrl=" + websiteUrl + ", frequency=" + frequency
				+ "]";
	}

}
