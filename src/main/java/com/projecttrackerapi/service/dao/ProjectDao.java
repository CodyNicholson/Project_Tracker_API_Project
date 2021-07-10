package com.projecttrackerapi.service.dao;

import com.projecttrackerapi.dtos.ProjectDto;
import com.projecttrackerapi.dtos.ProjectTaskDto;

import java.util.List;
import java.util.UUID;

public interface ProjectDao {
    ProjectDto saveOrUpdateProject(ProjectDto project);
    List<ProjectDto> findAllProjects();
    ProjectDto findProjectById(UUID id);
    ProjectDto deleteProject(UUID id);

    ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto);
    List<ProjectTaskDto> findAllProjectTasks();
    ProjectTaskDto findProjectTaskById(UUID id);
    ProjectTaskDto deleteProjectTaskById(UUID id);
    List<ProjectTaskDto> findProjectTasksByProjectId(UUID projectId);
}
