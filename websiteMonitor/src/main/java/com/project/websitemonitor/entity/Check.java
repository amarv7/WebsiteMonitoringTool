package com.project.websitemonitor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.websitemonitor.model.CheckDto;

@Entity
@Table(name = "checkTbl")
public class Check {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String website_url;
	private int frequency;
	private boolean enabled;

	public Check() {
	}

	public Check(CheckDto checkDto) {
		this.setName(checkDto.getName());
		this.setWebsite_url(checkDto.getWebsiteUrl());
		this.setFrequency(checkDto.getFrequency());
		this.setEnabled(true);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite_url() {
		return website_url;
	}

	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public CheckDto toDto() {
		CheckDto checkDto = new CheckDto(id, name, website_url, frequency);
		return checkDto;
	}
}
