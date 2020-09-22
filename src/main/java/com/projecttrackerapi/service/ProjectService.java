package com.projecttrackerapi.service;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dao.ProjectDao;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.models.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto saveOrUpdateProject(ProjectDto project) {
        String badRequestMessage = "";
        if (project.getName() == null || project.getName().isEmpty()) {
            badRequestMessage += Constants.PROJECT_MUST_HAVE_NAME;
        }
        if (project.getDescription() == null || project.getDescription().isEmpty()) {
            badRequestMessage += Constants.PROJECT_MUST_HAVE_DESCRIPTION;
        }
        if (!badRequestMessage.isEmpty()) {
            throw new BadRequestException(badRequestMessage.trim(), null);
        }

        if (project.getId() == null) {
            project.setId(UUID.randomUUID());
        }
        if (project.getStartDate() == null) {
            project.setStartDate(new Date());
        }

        return projectDao.saveOrUpdateProject(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectDao.findAllProjects();
    }

    public ProjectDto getProjectById(UUID projectId) {
        try {
            return projectDao.findProjectById(projectId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }
    }

    public DeleteProjectResponseModel deleteProjectById(UUID projectId) {
        try {
            return projectDao.deleteProject(projectId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }
    }
}
