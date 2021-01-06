package com.project.websitemonitor.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "websiteMonitorTbl")
public class WebsiteMonitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String website_url;
	private int checkId;
	private String status;
	private Timestamp uptime;
	private Timestamp downtime;
	private double responseTime;

	public WebsiteMonitor() {
	}

	public WebsiteMonitor(String website_url, int checkId, String status, Timestamp uptime, Timestamp downtime,
			double responseTime) {
		this.website_url = website_url;
		this.checkId = checkId;
		this.status = status;
		this.uptime = uptime;
		this.downtime = downtime;
		this.responseTime = responseTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWebsite_url() {
		return website_url;
	}

	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}

	public int getCheckId() {
		return checkId;
	}

	public void setCheckId(int checkId) {
		this.checkId = checkId;
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

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

}
