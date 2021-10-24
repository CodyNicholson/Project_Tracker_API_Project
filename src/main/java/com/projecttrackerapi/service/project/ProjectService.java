package com.projecttrackerapi.service.project;

import com.projecttrackerapi.dtos.ProjectRequestDto;
import com.projecttrackerapi.dtos.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDto);
    ProjectResponseDto readProjectById(String projectId);
    List<ProjectResponseDto> readAllProjects();
    ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, String projectId);
    ProjectResponseDto deleteProjectById(String projectId);
}
