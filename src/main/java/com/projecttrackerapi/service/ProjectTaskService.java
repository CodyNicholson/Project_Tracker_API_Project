package com.projecttrackerapi.service;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dao.ProjectDao;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.models.ProjectDto;
import com.projecttrackerapi.models.ProjectTaskDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectTaskService {
    private final ProjectDao projectDao;

    public ProjectTaskService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto) {
        try {
            projectDao.findProjectById(projectTaskDto.getId());
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_FOR_TASK_NOT_FOUND, null);
        }

        return projectDao.saveOrUpdateProjectTask(projectTaskDto);
    }

    public List<ProjectTaskDto> getAllProjectTasks() {
        return projectDao.findAllProjectTasks();
    }

    public ProjectTaskDto getProjectTaskById(UUID projectId) {
        return projectDao.findProjectTaskById(projectId);
    }

    public ProjectTaskDto deleteProjectTaskById(UUID projectId) {
        return projectDao.deleteProjectTasksByProjectId(projectId);
    }
}
