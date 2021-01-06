package com.project.websitemonitor.model;

import java.sql.Timestamp;

public class WebsiteMonitorDto {

	private String website_url;
	private String status;
	private Timestamp uptime;
	private Timestamp downtime;
	private double avgResponseTime;
	private String responseResult;

	public WebsiteMonitorDto() {
	}

	public WebsiteMonitorDto(String website_url, String status, Timestamp uptime, Timestamp downtime,
			double avgResponseTime, String responseResult) {
		this.website_url = website_url;
		this.status = status;
		this.uptime = uptime;
		this.downtime = downtime;
		this.avgResponseTime = avgResponseTime;
		this.responseResult = responseResult;
	}

	public String getWebsite_url() {
		return website_url;
	}

	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUptime() {
		return uptime;
	}

	public void setUptime(Timestamp uptime) {
		this.uptime = uptime;
	}

	public Timestamp getDowntime() {
		return downtime;
	}

	public void setDowntime(Timestamp downtime) {
		this.downtime = downtime;
	}

	public double getAvgResponseTime() {
		return avgResponseTime;
	}

	public void setAvgResponseTime(double avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public String getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

}
