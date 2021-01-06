package com.project.websitemonitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.project.websitemonitor.entity.Check;
import com.project.websitemonitor.entity.WebsiteMonitor;

@SpringBootApplication
public class WebsiteMonitorApplication {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(WebsiteMonitorApplication.class, args);
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setAnnotatedClasses(Check.class, WebsiteMonitor.class);
		return sessionFactory;
	}

	@Bean
	public ScheduledExecutorService threadPoolTaskScheduler() {
		ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);
		return scheduledExecutor;
	}

}
