package com.projecttrackerapi.service.project.impl;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dtos.ProjectRequestDto;
import com.projecttrackerapi.service.dao.impl.ProjectDaoImpl;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.dtos.ProjectResponseDto;
import com.projecttrackerapi.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDaoImpl projectDao;

    public ProjectServiceImpl(ProjectDaoImpl projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        validateProjectDto(projectRequestDto);

        if (projectRequestDto.getStart_date() == null) {
            projectRequestDto.setStart_date(new Date());
        }

        return projectDao.createProject(projectRequestDto);
    }

    @Override
    public ProjectResponseDto readProjectById(String projectId) {
        return projectDao.findProjectById(UUID.fromString(projectId));
    }

    @Override
    public List<ProjectResponseDto> readAllProjects() {
        return projectDao.findAllProjects();
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, String projectId) {
        validateProjectDto(projectRequestDto);

        UUID projectUUID = UUID.fromString(projectId);

        return projectDao.updateProject(projectRequestDto, projectUUID);
    }

    @Override
    public ProjectResponseDto deleteProjectById(String projectId) {
        return projectDao.deleteProject(UUID.fromString(projectId));
    }

    private void validateProjectDto(ProjectRequestDto projectRequestDto) {
        String badRequestMessage = "";
        if (projectRequestDto.getName() == null || projectRequestDto.getName().isEmpty()) {
            badRequestMessage += Constants.PROJECT_MUST_HAVE_NAME;
        }
        if (projectRequestDto.getDescription() == null || projectRequestDto.getDescription().isEmpty()) {
            badRequestMessage += Constants.PROJECT_MUST_HAVE_DESCRIPTION;
        }
        if (!badRequestMessage.isEmpty()) {
            throw new BadRequestException(badRequestMessage.trim(), null);
        }
    }
}
