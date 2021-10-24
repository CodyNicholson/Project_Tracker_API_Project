package com.projecttrackerapi.service.projecttask.impl;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dtos.ProjectTaskRequestDto;
import com.projecttrackerapi.service.dao.impl.ProjectDaoImpl;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.dtos.ProjectTaskResponseDto;
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

    @Override
    public ProjectTaskResponseDto createProjectTask(ProjectTaskRequestDto projectTaskRequestDto, String projectId) {
        validateProjectTask(projectTaskRequestDto, projectId);

        return projectDao.createProjectTask(projectTaskRequestDto, UUID.fromString(projectId));
    }

    @Override
    public ProjectTaskResponseDto getProjectTaskById(String projectTaskId, String projectId) {
        return projectDao.findProjectTaskById(UUID.fromString(projectTaskId), UUID.fromString(projectId));
    }

    @Override
    public List<ProjectTaskResponseDto> getProjectTasksByProjectId(String projectId) {
        UUID projectUUID = UUID.fromString(projectId);

        return projectDao.findProjectTasksByProjectId(projectUUID);
    }

    @Override
    public ProjectTaskResponseDto updateProjectTask(ProjectTaskRequestDto projectTaskRequestDto, String projectTaskId, String projectId) {
        validateProjectTask(projectTaskRequestDto, projectId);

        projectDao.findProjectById(UUID.fromString(projectId));

        if(projectTaskRequestDto.getStatus() == null || projectTaskRequestDto.getStatus().isEmpty()){
            projectTaskRequestDto.setStatus(Constants.TODO_STATUS);
        }

        return projectDao.updateProjectTask(projectTaskRequestDto, UUID.fromString(projectTaskId), UUID.fromString(projectId));
    }

    @Override
    public ProjectTaskResponseDto deleteProjectTaskById(String projectTaskId, String projectId) {
        return projectDao.deleteProjectTaskById(UUID.fromString(projectTaskId), UUID.fromString(projectId));
    }

    private void validateProjectTask(ProjectTaskRequestDto projectTaskRequestDto, String projectId) {
        String badRequestMessage = "";

        if (projectId == null) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_PROJECT_ID;
        }

        if (projectTaskRequestDto.getName() == null || projectTaskRequestDto.getName().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_NAME;
        }

        if (projectTaskRequestDto.getDescription() == null || projectTaskRequestDto.getDescription().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_DESCRIPTION;
        }

        if (projectTaskRequestDto.getAcceptance_criteria() == null || projectTaskRequestDto.getAcceptance_criteria().equals("")) {
            badRequestMessage += Constants.PROJECT_TASK_MUST_HAVE_ACCEPTANCE_CRITERIA;
        }

        if (!badRequestMessage.isEmpty()) {
            throw new BadRequestException(badRequestMessage.trim(), null);
        }
    }
}
