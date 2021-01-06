package com.project.websitemonitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.websitemonitor.WebsiteMonitorApplication;
import com.project.websitemonitor.repository.WebsiteMonitorRepository;
import com.project.websitemonitor.service.WebsiteMonitorService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebsiteMonitorService.class, WebsiteMonitorRepository.class })
@SpringBootTest(classes = WebsiteMonitorApplication.class)
public class WebsiteMonitorSerivceTest {

	@InjectMocks
	private WebsiteMonitorService websiteMonitorService;

	@Mock
	private WebsiteMonitorRepository websiteMonitorRepository;

	@Test
	public void registerCheck() {

	}

	@Test
	public void disableCheck() {

	}

	@Test
	public void enableCheck() {

	}

	@Test
	public void getAllChecks() {

	}

	@Test
	public void getAllChecksByName() {

	}

	@Test
	public void getAllChecksByInterval() {

	}

	@Test
	public void prepareWebsiteMonitorJobs() {

	}

	@Test
	public void getWebsiteStatus() {

	}

}
