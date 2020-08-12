package com.codynicholson.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectboardApplication {

	public static void main(String[] args) {
		//JDBC URL: jdbc:h2:mem:testdb
		SpringApplication.run(ProjectboardApplication.class, args);
	}

}

