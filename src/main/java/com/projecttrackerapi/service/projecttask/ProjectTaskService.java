package com.projecttrackerapi.service.projecttask;

import com.projecttrackerapi.dtos.ProjectTaskRequestDto;
import com.projecttrackerapi.dtos.ProjectTaskResponseDto;

import java.util.List;

public interface ProjectTaskService {
    ProjectTaskResponseDto createProjectTask(ProjectTaskRequestDto projectTaskRequestDto, String projectId);
    ProjectTaskResponseDto getProjectTaskById(String projectTaskId, String projectId);
    List<ProjectTaskResponseDto> getProjectTasksByProjectId(String projectId);
    ProjectTaskResponseDto updateProjectTask(ProjectTaskRequestDto projectTaskRequestDto, String projectTaskId, String projectId);
    ProjectTaskResponseDto deleteProjectTaskById(String projectTaskId, String projectId);
}
