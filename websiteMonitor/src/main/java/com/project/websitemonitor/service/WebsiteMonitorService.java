package com.project.websitemonitor.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.websitemonitor.common.Status;
import com.project.websitemonitor.common.Utility;
import com.project.websitemonitor.entity.Check;
import com.project.websitemonitor.entity.WebsiteMonitor;
import com.project.websitemonitor.model.CheckDto;
import com.project.websitemonitor.model.WebsiteMonitorDto;
import com.project.websitemonitor.repository.WebsiteMonitorRepository;

@Service
public class WebsiteMonitorService {

	@Autowired
	private WebsiteMonitorRepository websiteMonitorRepository;

	@Autowired
	private ScheduledExecutorService scheduledExecutor;

	private Map<String, ScheduledFuture<?>> scheduledJobMap;

	/**
	 * Register a check created by user for Monitoring a Website is up/down.
	 * 
	 * @param checkDto
	 * @return checkDto
	 */
	public CheckDto registerCheck(CheckDto checkDto) {
		String url = Utility.trimHttpFromUrl(checkDto.getWebsiteUrl());
		checkDto.setWebsiteUrl(url);
		Check check = websiteMonitorRepository.saveCheck(new Check(checkDto));
		submitJobToScheduler(check.getFrequency(), check.getId(), check.getWebsite_url());
		return check.toDto();
	}

	/**
	 * To disable any check, disabled check will be stopped to do Monitoring
	 * 
	 * @param checkDto
	 */
	public void disableCheck(CheckDto checkDto) {
		websiteMonitorRepository.updateCheck(false, checkDto.getId());
		cancelScheduledJob(Utility.trimHttpFromUrl(checkDto.getWebsiteUrl()));
	}

	/**
	 * To enable or activate any disabled check, enable check will be resumed to do
	 * Monitoring
	 * 
	 * @param checkDto
	 */
	public void enableCheck(CheckDto checkDto) {
		websiteMonitorRepository.updateCheck(true, checkDto.getId());
		Check check = websiteMonitorRepository.getCheck(checkDto.getId());
		submitJobToScheduler(check.getFrequency(), check.getId(), check.getWebsite_url());
	}

	/**
	 * Return all created checks along with all the details
	 * 
	 * @return List of all created checks
	 */
	public List<CheckDto> getAllChecks() {
		List<Check> allChecks = websiteMonitorRepository.getAllChecks();
		List<CheckDto> checkDtosList = allChecks.stream().map(obj -> obj.toDto()).collect(Collectors.toList());
		return checkDtosList;
	}

	/**
	 * Return all the checks filtered by name
	 * 
	 * @param name
	 * @return filtered checks by Name
	 */
	public List<CheckDto> getAllChecksByName(String name) {
		List<Check> allChecks = websiteMonitorRepository.getFilteredChecks(name);
		List<CheckDto> checkDtosList = allChecks.stream().map(obj -> obj.toDto()).collect(Collectors.toList());
		return checkDtosList;
	}

	/**
	 * Return all the checks filtered by Interval(frequency)
	 * 
	 * @param name
	 * @return filtered checks by Interval
	 */
	public List<CheckDto> getAllChecksByInterval(int interval) {
		List<Check> allChecks = websiteMonitorRepository.getFilteredChecks(interval);
		List<CheckDto> checkDtosList = allChecks.stream().map(obj -> obj.toDto()).collect(Collectors.toList());
		return checkDtosList;
	}

	/**
	 * Fetch the active checks from database and submits them for scheduling to
	 * monitor websites
	 */
	private void prepareWebsiteMonitorJobs() {
		List<Check> activeChecks = websiteMonitorRepository.getActiveChecks();
		activeChecks.stream().forEach(check -> {
			submitJobToScheduler(check.getFrequency(), check.getId(), check.getWebsite_url());
		});
	}

	/**
	 * Logic has been implemented to take decision of rounding frequency in
	 * minutes/hours as passed by the user
	 * 
	 * @param frequency
	 * @param checkId
	 * @param websiteUrl
	 */
	private void submitJobToScheduler(double frequency, int checkId, String websiteUrl) {
		double hourMin = 60;
		long period = (long) frequency;
		TimeUnit unit = TimeUnit.MINUTES;
		if (frequency >= hourMin) {
			period = Math.round(frequency / hourMin);
			unit = TimeUnit.HOURS;
		}
		ScheduledFuture<?> scheduledJob = executeJobScheduler(checkId, websiteUrl, period, unit);
		scheduledJobMap.put(websiteUrl, scheduledJob);
	}

	/**
	 * Method pings the particular Url record its response time and up/down status
	 * 
	 * @param monitor
	 */
	private void pingURL(WebsiteMonitor monitor) {
		String url = monitor.getWebsite_url();
		try (Socket socket = new Socket()) {
			long start = System.currentTimeMillis();
			socket.connect(new InetSocketAddress(url, 80), 3000);
			long end = System.currentTimeMillis();
			monitor.setResponseTime(end - start);
			monitor.setUptime(Utility.getFormattedDate(end));
			monitor.setStatus(Status.UP.getStatus());
		} catch (IOException e) {
			monitor.setStatus(Status.DOWN.getStatus());
			monitor.setDowntime(Utility.getFormattedDate(System.currentTimeMillis()));
			sendEmail(url + " is Not Reachable");
			e.printStackTrace();
		}
	}

	/**
	 * Method starts the process of creating and scheduling jobs for monitoring
	 * websites soon after the application is up
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		scheduledJobMap = new HashMap<String, ScheduledFuture<?>>();
		this.prepareWebsiteMonitorJobs();
	}

	/**
	 * Returns the website status along with details like - UP/Down status,
	 * uptime/downtime, URL and Average response time
	 * 
	 * @param websiteUrl
	 * @return dto which contains status of Website
	 */
	public WebsiteMonitorDto getWebsiteStatus(String websiteUrl) {
		WebsiteMonitor websiteStatus = websiteMonitorRepository.getWebsiteStatus(websiteUrl);
		String website_url = websiteStatus.getWebsite_url();
		String status = websiteStatus.getStatus();
		Timestamp uptime = websiteStatus.getUptime();
		Timestamp downtime = websiteStatus.getDowntime();
		double avgResponseTime = getAvgResponseTime(website_url);
		WebsiteMonitorDto websiteMonitorDto = new WebsiteMonitorDto(website_url, status, uptime, downtime,
				avgResponseTime, null);
		return websiteMonitorDto;
	}

	/**
	 * Method calculates the average response time of website, by considering last
	 * few records ping time
	 * 
	 * @param website_url
	 * @return average response time
	 */
	private double getAvgResponseTime(String website_url) {
		List<WebsiteMonitor> websiteStatusForAvgResponse = websiteMonitorRepository
				.getWebsiteStatusForAvgResponse(website_url);
		double aggregateResponseTime = websiteStatusForAvgResponse.stream().mapToDouble(s -> s.getResponseTime()).sum();
		return aggregateResponseTime / (double) websiteStatusForAvgResponse.size();
	}

	/**
	 * Schedule a Monitoring Job at fixed rate to record the ping/response status of
	 * a website
	 * 
	 * @param checkId
	 * @param websiteUrl
	 * @param period     in hours/minutes
	 * @param Time       unit
	 * @return scheduled future job
	 */
	private ScheduledFuture<?> executeJobScheduler(int checkId, String websiteUrl, long period, TimeUnit unit) {
		ScheduledFuture<?> scheduledJob = scheduledExecutor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				WebsiteMonitor monitor = new WebsiteMonitor();
				monitor.setCheckId(checkId);
				monitor.setWebsite_url(websiteUrl);
				pingURL(monitor);
				websiteMonitorRepository.saveWebsiteMonitor(monitor);
			}
		}, 0, period, unit);

		return scheduledJob;
	}

	/**
	 * Cancel the Monitoring Job of website if Check is been set disable/deactivate
	 * 
	 * @param websiteUrl
	 */
	private void cancelScheduledJob(String websiteUrl) {
		ScheduledFuture<?> scheduledFuture = scheduledJobMap.get(websiteUrl);
		boolean isCancelled = scheduledFuture.cancel(true);
		if (isCancelled) {
			scheduledJobMap.remove(websiteUrl);
		}
	}

	/**
	 * TODO: Logic is to be implemented for sending email to the user if Website is
	 * down.
	 * 
	 * @param message
	 */
	private void sendEmail(String message) {
		System.out.println(message);
	}

}
