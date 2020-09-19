package com.projecttrackerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProjectTrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectTrackerApplication.class, args);
	}
}
