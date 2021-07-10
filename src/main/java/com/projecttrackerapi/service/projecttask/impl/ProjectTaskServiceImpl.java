package com.projecttrackerapi.service.projecttask.impl;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.service.dao.impl.ProjectDaoImpl;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.dtos.ProjectTaskDto;
import com.projecttrackerapi.service.projecttask.ProjectTaskService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
    private final ProjectDaoImpl projectDao;

    public ProjectTaskServiceImpl(ProjectDaoImpl projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto) {
        projectDao.findProjectById(projectTaskDto.getProject_id());

        String badRequestMessage = "";
        if (projectTaskDto.getProject_id() == null) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_PROJECT_ID;
        }
        if (projectTaskDto.getName() == null || projectTaskDto.getName().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_NAME;
        }
        if (projectTaskDto.getDescription() == null || projectTaskDto.getDescription().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_DESCRIPTION;
        }
        if (projectTaskDto.getAcceptance_criteria() == null || projectTaskDto.getAcceptance_criteria().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_ACCEPTANCE_CRITERIA;
        }
        if (!badRequestMessage.isEmpty()) {
            throw new BadRequestException(badRequestMessage.trim(), null);
        }

        if(projectTaskDto.getStatus() == null || projectTaskDto.getStatus().isEmpty()){
            projectTaskDto.setStatus(Constants.TODO_STATUS);
        }

        return projectDao.saveOrUpdateProjectTask(projectTaskDto);
    }

    public List<ProjectTaskDto> getAllProjectTasks() {
        return projectDao.findAllProjectTasks();
    }

    public ProjectTaskDto getProjectTaskById(UUID projectTaskId) {
        return projectDao.findProjectTaskById(projectTaskId);
    }

    public List<ProjectTaskDto> getProjectTasksByProjectId(UUID projectId) {
        projectDao.findProjectById(projectId);

        List<ProjectTaskDto> projectTaskDtos = projectDao.findProjectTasksByProjectId(projectId);
        if (projectTaskDtos.isEmpty()) {
            throw new NotFoundException(Constants.PROJECT_HAS_NO_TASKS, null);
        }

        return projectTaskDtos;
    }

    public ProjectTaskDto deleteProjectTaskById(UUID projectId) {
        return projectDao.deleteProjectTaskById(projectId);
    }
}
