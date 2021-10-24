package com.projecttrackerapi.service.dao;

import com.projecttrackerapi.dtos.ProjectRequestDto;
import com.projecttrackerapi.dtos.ProjectResponseDto;
import com.projecttrackerapi.dtos.ProjectTaskRequestDto;
import com.projecttrackerapi.dtos.ProjectTaskResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProjectDao {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDto);
    List<ProjectResponseDto> findAllProjects();
    ProjectResponseDto findProjectById(UUID id);
    ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, UUID projectId);
    ProjectResponseDto deleteProject(UUID id);

    ProjectTaskResponseDto createProjectTask(ProjectTaskRequestDto projectTaskRequestDto, UUID projectId);
    ProjectTaskResponseDto findProjectTaskById(UUID projectTaskId, UUID projectId);
    List<ProjectTaskResponseDto> findProjectTasksByProjectId(UUID projectId);
    ProjectTaskResponseDto updateProjectTask(ProjectTaskRequestDto projectTaskRequestDto, UUID projectTaskId, UUID projectId);
    ProjectTaskResponseDto deleteProjectTaskById(UUID projectTaskId, UUID projectId);
}
