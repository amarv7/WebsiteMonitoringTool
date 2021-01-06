package com.project.websitemonitor.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.doNothing;

import com.project.websitemonitor.model.CheckDto;
import com.project.websitemonitor.model.WebsiteMonitorDto;
import com.project.websitemonitor.service.WebsiteMonitorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebsiteMonitorControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private WebsiteMonitorService websiteMonitorService;

	@LocalServerPort
	int randomServerPort;

	private CheckDto checkDto1;
	private CheckDto checkDto2;
	private CheckDto checkDto3;
	private List<CheckDto> listCheckDto;
	private String baseUrl;

	@Before
	public void init() {
		baseUrl = "http://localhost:" + randomServerPort;
		checkDto1 = new CheckDto(1, "java-api1", "www.springboottutorial.com", 30);
		checkDto2 = new CheckDto(2, "java-api2", "www.facebook.com", 120);
		checkDto3 = new CheckDto(3, "java-api3", "www.google.com", 90);
	}

	@Test
	public void registerCheck() throws Exception {
		Mockito.when(websiteMonitorService.registerCheck(Mockito.any())).thenReturn(checkDto1);

		baseUrl = baseUrl + "/check/register";
		URI uri = new URI(baseUrl);
		CheckDto checkDto = new CheckDto("java-api1", "www.springboottutorial.com", 30);
		HttpEntity<CheckDto> request = new HttpEntity<>(checkDto);

		ResponseEntity<CheckDto> result = this.restTemplate.postForEntity(uri, request, CheckDto.class);

		assertEquals(201, result.getStatusCodeValue());
		assertEquals(1, result.getBody().getId());
		assertEquals(30, result.getBody().getFrequency());
		assertEquals("java-api1", result.getBody().getName());
		assertEquals("www.springboottutorial.com", result.getBody().getWebsiteUrl());
	}

	@Test
	public void getAllChecks() throws URISyntaxException {
		listCheckDto = new ArrayList<CheckDto>();
		listCheckDto.add(checkDto1);
		listCheckDto.add(checkDto2);
		listCheckDto.add(checkDto3);
		Mockito.when(websiteMonitorService.getAllChecks()).thenReturn(listCheckDto);

		baseUrl = baseUrl + "/check/view";
		URI uri = new URI(baseUrl);

		ResponseEntity<CheckDto[]> result = this.restTemplate.getForEntity(uri, CheckDto[].class);
		List<CheckDto> checkDtoList = Arrays.asList(result.getBody());
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(3, checkDtoList.size());
		assertEquals(3, checkDtoList.get(2).getId());
		assertEquals("java-api3", checkDtoList.get(2).getName());
		assertEquals(90, checkDtoList.get(2).getFrequency());
		assertEquals("www.google.com", checkDtoList.get(2).getWebsiteUrl());
	}

	@Test
	public void getAllChecksByName() throws URISyntaxException {
		listCheckDto = new ArrayList<CheckDto>();
		listCheckDto.add(checkDto1);
		listCheckDto.add(checkDto2);
		listCheckDto.add(checkDto3);
		Mockito.when(websiteMonitorService.getAllChecksByName(Mockito.anyString())).thenReturn(listCheckDto);

		String url = baseUrl + "/check/view/byname?name={name}";

		ResponseEntity<CheckDto[]> result = this.restTemplate.getForEntity(url, CheckDto[].class, "java-api");
		List<CheckDto> checkDtoList = Arrays.asList(result.getBody());
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(3, checkDtoList.size());
		assertEquals("java-api1", checkDtoList.get(0).getName());
		assertEquals("java-api2", checkDtoList.get(1).getName());
		assertEquals("java-api3", checkDtoList.get(2).getName());
	}

	@Test
	public void getAllChecksByInterval() throws URISyntaxException {
		listCheckDto = new ArrayList<CheckDto>();
		listCheckDto.add(checkDto1);
		listCheckDto.add(new CheckDto(4, "java-api4", "www.yahoo.com", 30));
		Mockito.when(websiteMonitorService.getAllChecksByInterval(Mockito.anyInt())).thenReturn(listCheckDto);

		String url = baseUrl + "/check/view/byinterval?interval={interval}";

		ResponseEntity<CheckDto[]> result = this.restTemplate.getForEntity(url, CheckDto[].class, "30");
		List<CheckDto> checkDtoList = Arrays.asList(result.getBody());
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(2, checkDtoList.size());
		assertEquals(30, checkDtoList.get(0).getFrequency());
		assertEquals(30, checkDtoList.get(1).getFrequency());
	}

	@Test
	public void disableCheck() throws URISyntaxException {

		doNothing().when(websiteMonitorService).disableCheck(Mockito.any());

		baseUrl = baseUrl + "/check/disable";
		URI uri = new URI(baseUrl);
		CheckDto checkDto = new CheckDto(1, "java-api1", "www.springboottutorial.com", 30);
		HttpEntity<CheckDto> request = new HttpEntity<>(checkDto);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Check Disabled Succesfully", response.getBody());
	}

	@Test
	public void enableCheck() throws URISyntaxException {
		doNothing().when(websiteMonitorService).enableCheck(Mockito.any());

		baseUrl = baseUrl + "/check/enable";
		URI uri = new URI(baseUrl);
		CheckDto checkDto = new CheckDto(1, "java-api1", "www.springboottutorial.com", 30);
		HttpEntity<CheckDto> request = new HttpEntity<>(checkDto);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Check enabled Succesfully", response.getBody());
	}

	@Test
	public void getWebsiteStatus() throws URISyntaxException {
		Timestamp uptime = new Timestamp(1L);
		Timestamp downtime = new Timestamp(1L);
		WebsiteMonitorDto websiteStatus = new WebsiteMonitorDto("www.google.com", "UP", uptime, downtime, 123, null);
		Mockito.when(websiteMonitorService.getWebsiteStatus(Mockito.anyString())).thenReturn(websiteStatus);

		String url = baseUrl + "/website/status?websiteUrl={websiteUrl}";

		ResponseEntity<WebsiteMonitorDto> result = this.restTemplate.getForEntity(url, WebsiteMonitorDto.class, "www.google.com");

		assertEquals(200, result.getStatusCodeValue());
		assertEquals("UP", result.getBody().getStatus());
		assertEquals("www.google.com", result.getBody().getWebsite_url());
		assertEquals(uptime, result.getBody().getUptime());
		assertEquals(123.0, result.getBody().getAvgResponseTime(), 0.001);
	}

}
