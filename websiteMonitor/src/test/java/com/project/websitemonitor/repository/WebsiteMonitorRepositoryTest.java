package com.project.websitemonitor.repository;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.project.websitemonitor.WebsiteMonitorApplication;
import com.project.websitemonitor.entity.Check;
import com.project.websitemonitor.entity.WebsiteMonitor;
import com.project.websitemonitor.model.CheckDto;
import com.project.websitemonitor.repository.WebsiteMonitorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebsiteMonitorApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class WebsiteMonitorRepositoryTest {

	@Resource
	private WebsiteMonitorRepository websiteMonitorRepository;

	private List<Check> savedCheckList;

	@Before
	public void init() {
		CheckDto checkDto1 = new CheckDto("Test-Data", "www.Google.com", 30);
		CheckDto checkDto2 = new CheckDto("Test-Data", "www.Gmail.com", 30);
		CheckDto checkDto3 = new CheckDto("Test-API", "www.Amazon.com", 60);
		CheckDto checkDto4 = new CheckDto("Test-API", "www.Yahoo.com", 60);
		Check saveCheck1 = websiteMonitorRepository.saveCheck(new Check(checkDto1));
		Check saveCheck2 = websiteMonitorRepository.saveCheck(new Check(checkDto2));
		Check saveCheck3 = websiteMonitorRepository.saveCheck(new Check(checkDto3));
		Check saveCheck4 = websiteMonitorRepository.saveCheck(new Check(checkDto4));
		savedCheckList = new ArrayList<Check>();
		savedCheckList.add(saveCheck1);
		savedCheckList.add(saveCheck2);
		savedCheckList.add(saveCheck3);
		savedCheckList.add(saveCheck4);
	}

	@Test
	public void saveCheck() {
		CheckDto checkDto = new CheckDto("Test-Data2", "www.Facebook.com", 120);
		Check check1 = websiteMonitorRepository.saveCheck(new Check(checkDto));
		Check check2 = websiteMonitorRepository.getCheck(check1.getId());

		assertEquals(check1.getName(), check2.getName());
		assertEquals(check1.getFrequency(), check2.getFrequency());
		assertEquals(check1.getWebsite_url(), check2.getWebsite_url());
	}

	@Test
	public void updateCheck() {
		websiteMonitorRepository.updateCheck(false, savedCheckList.get(0).getId());
	}

	@Test
	public void getAllChecks() {
		List<Check> allChecks = websiteMonitorRepository.getAllChecks();
		assertEquals(4, allChecks.size());
	}

	@Test
	public void getFilteredChecksByName() {
		List<Check> filteredChecksByName = websiteMonitorRepository.getFilteredChecks("Test-Data");
		assertEquals(2, filteredChecksByName.size());
	}

	@Test
	public void getFilteredChecksByInterval() {
		List<Check> filteredChecksByInterval = websiteMonitorRepository.getFilteredChecks(60);
		assertEquals(2, filteredChecksByInterval.size());
	}

	@Test
	public void getActiveChecks() {
		List<Check> activeChecks = websiteMonitorRepository.getActiveChecks();
		assertEquals(4, activeChecks.size());
	}

	@Test
	public void getCheck() {
		Check check = websiteMonitorRepository.getCheck(savedCheckList.get(1).getId());
		assertEquals("Test-Data", check.getName());
		assertEquals(30, check.getFrequency());
		assertEquals("www.Gmail.com", check.getWebsite_url());

	}

	@Test
	public void saveWebsiteMonitor() {
		Timestamp uptime = new Timestamp(1L);
		Timestamp downtime = new Timestamp(1L);
		WebsiteMonitor monitor = new WebsiteMonitor("www.java-spring.com", 69, "UP", uptime, downtime, 200);
		websiteMonitorRepository.saveWebsiteMonitor(monitor);
		WebsiteMonitor websiteStatus = websiteMonitorRepository.getWebsiteStatus("www.java-spring.com");
		assertEquals(69, websiteStatus.getCheckId());
		assertEquals(200, websiteStatus.getResponseTime(), 0.001);
		assertEquals("UP", websiteStatus.getStatus());
		assertEquals("www.java-spring.com", websiteStatus.getWebsite_url());
	}

	@Test
	public void getWebsiteStatusForAvgResponse() {
		Timestamp uptime = new Timestamp(1L);
		Timestamp downtime = new Timestamp(1L);
		WebsiteMonitor monitor = new WebsiteMonitor("www.java-spring.com", 69, "UP", uptime, downtime, 150);
		websiteMonitorRepository.saveWebsiteMonitor(monitor);
		monitor = new WebsiteMonitor("www.java-spring.com", 69, "UP", uptime, downtime, 200);
		websiteMonitorRepository.saveWebsiteMonitor(monitor);
		List<WebsiteMonitor> websiteMonitorForAvgResponse = websiteMonitorRepository
				.getWebsiteStatusForAvgResponse("www.java-spring.com");
		WebsiteMonitor websiteMonitor1 = websiteMonitorForAvgResponse.get(0);
		WebsiteMonitor websiteMonitor2 = websiteMonitorForAvgResponse.get(1);
		assertEquals(175.0, (websiteMonitor1.getResponseTime() + websiteMonitor2.getResponseTime()) / 2, 0.001);
	}

}
