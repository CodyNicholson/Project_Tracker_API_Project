package com.projecttrackerapi.service.projecttask;

import com.projecttrackerapi.dtos.ProjectTaskDto;

import java.util.List;
import java.util.UUID;

public interface ProjectTaskService {
    ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto);
    List<ProjectTaskDto> getAllProjectTasks();
    ProjectTaskDto getProjectTaskById(UUID projectTaskId);
    List<ProjectTaskDto> getProjectTasksByProjectId(UUID projectId);
    ProjectTaskDto deleteProjectTaskById(UUID projectId);
}
