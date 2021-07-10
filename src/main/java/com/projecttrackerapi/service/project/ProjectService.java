package com.projecttrackerapi.service.project;

import com.projecttrackerapi.dtos.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDto saveOrUpdateProject(ProjectDto project);
    List<ProjectDto> getAllProjects();
    ProjectDto getProjectById(UUID projectId);
    ProjectDto deleteProjectById(UUID projectId);
}
