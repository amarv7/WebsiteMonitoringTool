package com.project.websitemonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.websitemonitor.model.CheckDto;
import com.project.websitemonitor.model.WebsiteMonitorDto;
import com.project.websitemonitor.service.WebsiteMonitorService;

@RestController
public class WebsiteMonitorController {

	@Autowired
	private WebsiteMonitorService websiteMonitorService;

	@RequestMapping(value = "/check/register", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<CheckDto> registerCheck(@RequestBody CheckDto checkDto) {
		CheckDto registeredCheckDto = websiteMonitorService.registerCheck(checkDto);
		return new ResponseEntity<CheckDto>(registeredCheckDto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/check/view", method = RequestMethod.GET)
	public ResponseEntity<List<CheckDto>> getAllChecks() {
		List<CheckDto> checkDtoList = websiteMonitorService.getAllChecks();
		return new ResponseEntity<List<CheckDto>>(checkDtoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/check/view/byname", method = RequestMethod.GET)
	public ResponseEntity<List<CheckDto>> getAllChecksByName(@RequestParam(required = true) String name) {
		List<CheckDto> checkDtoList = websiteMonitorService.getAllChecksByName(name);
		return new ResponseEntity<List<CheckDto>>(checkDtoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/check/view/byinterval", method = RequestMethod.GET)
	public ResponseEntity<List<CheckDto>> getAllChecksByInterval(@RequestParam(required = true) int interval) {
		List<CheckDto> checkDtoList = websiteMonitorService.getAllChecksByInterval(interval);
		return new ResponseEntity<List<CheckDto>>(checkDtoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/check/disable", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<String> disableCheck(@RequestBody CheckDto checkDto) {
		websiteMonitorService.disableCheck(checkDto);
		return new ResponseEntity<String>("Check Disabled Succesfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/check/enable", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<String> enableCheck(@RequestBody CheckDto checkDto) {
		websiteMonitorService.enableCheck(checkDto);
		return new ResponseEntity<String>("Check enabled Succesfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/website/status", method = RequestMethod.GET)
	public ResponseEntity<WebsiteMonitorDto> getWebsiteStatus(@RequestParam(required = true) String websiteUrl) {
		WebsiteMonitorDto websiteStatus = websiteMonitorService.getWebsiteStatus(websiteUrl);
		return new ResponseEntity<WebsiteMonitorDto>(websiteStatus, HttpStatus.OK);
	}

}
