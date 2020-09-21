package com.projecttrackerapi.service;

import com.projecttrackerapi.dao.ProjectDao;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.models.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto saveOrUpdateProject(ProjectDto project) {
        if (project.getName() == null || project.getName().isEmpty()) {
            throw new BadRequestException("CONST ME", null);
        }
        return projectDao.saveOrUpdateProject(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectDao.findAllProjects();
    }

    public ProjectDto getProjectById(UUID projectId) {
        return projectDao.findProjectById(projectId);
    }

    public DeleteProjectResponseModel deleteProjectById(UUID projectId) {
        return projectDao.deleteProject(projectId);
    }
}
