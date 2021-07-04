package com.projecttrackerapi.config;

import com.projecttrackerapi.ProjectTrackerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(ProjectTrackerApplication.class);
    }
}
