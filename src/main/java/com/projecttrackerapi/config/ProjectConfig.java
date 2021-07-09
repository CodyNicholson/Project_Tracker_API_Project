package com.projecttrackerapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projecttrackerapi.ProjectTrackerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ProjectConfig {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(ProjectTrackerApplication.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean //NOTE: Used to render swagger-ui
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
