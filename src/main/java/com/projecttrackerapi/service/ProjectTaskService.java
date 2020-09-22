package com.projecttrackerapi.service;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dao.ProjectDao;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
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
        String badRequestMessage = "";
        if (projectTaskDto.getProjectId() == null) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_PROJECT_ID;
        }
        if (projectTaskDto.getName() == null || projectTaskDto.getName().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_NAME;
        }
        if (projectTaskDto.getDescription() == null || projectTaskDto.getDescription().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_DESCRIPTION;
        }
        if (projectTaskDto.getAcceptanceCriteria() == null || projectTaskDto.getAcceptanceCriteria().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_ACCEPTANCE_CRITERIA;
        }
        if (!badRequestMessage.isEmpty()) {
            throw new BadRequestException(badRequestMessage.trim(), null);
        }

        if(projectTaskDto.getStatus() == null || projectTaskDto.getStatus().isEmpty()){
            projectTaskDto.setStatus(Constants.TODO_STATUS);
        }

        try {
            projectDao.findProjectById(projectTaskDto.getProjectId());
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_FOR_TASK_NOT_FOUND, null);
        }

        return projectDao.saveOrUpdateProjectTask(projectTaskDto);
    }

    public List<ProjectTaskDto> getAllProjectTasks() {
        return projectDao.findAllProjectTasks();
    }

    public ProjectTaskDto getProjectTaskById(UUID projectTaskId) {
        try {
            return projectDao.findProjectTaskById(projectTaskId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }
    }

    public ProjectTaskDto deleteProjectTaskById(UUID projectId) {
        try {
            return projectDao.deleteProjectTaskById(projectId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }
    }
}
