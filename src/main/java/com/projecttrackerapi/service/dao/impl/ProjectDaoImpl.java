package com.projecttrackerapi.service.dao.impl;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dtos.ProjectDto;
import com.projecttrackerapi.dtos.ProjectTaskDto;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import com.projecttrackerapi.service.dao.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectDto saveOrUpdateProject(ProjectDto project) {
        log.info(Constants.saveOrUpdateProjectMessage(project));
        Project savedProject = projectRepository.save(projectDtoToEntity(project));
        return projectEntityToDto(savedProject);
    }

    public List<ProjectDto> findAllProjects() {
        return projectEntitiesToDtos(projectRepository.findAll());
    }

    public ProjectDto findProjectById(UUID id){
        Project project = projectRepository.getById(id);

        if (project == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        return projectEntityToDto(project);
    }

    @Transactional
    public ProjectDto deleteProject(UUID id) {
        Project project = projectRepository.getById(id);

        if (project == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        List<ProjectTask> projectTasks = projectTaskRepository.getByProjectId(id);
        projectTasks.forEach(projectTask -> projectTaskRepository.delete(projectTask));

        projectRepository.delete(project);

        ProjectDto deletedProjectDto = projectEntityToDto(project);
        List<ProjectTaskDto> deletedProjectTaskDtos = projectTaskEntitiesToDtos(projectTasks);

        log.info(Constants.deleteProjectMessage(deletedProjectDto, deletedProjectTaskDtos));

        return deletedProjectDto;
    }

    public ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto) {
        log.info(Constants.saveOrUpdateProjectTaskMessage(projectTaskDto));

        Project project = projectRepository.getById(projectTaskDto.getProject_id());

        ProjectTask projectTask = projectTaskDtoToEntity(projectTaskDto);
        projectTask.setProject(project);

        ProjectTask savedProjectTask = projectTaskRepository.save(projectTask);
        return projectTaskEntityToDto(savedProjectTask);
    }

    public List<ProjectTaskDto> findAllProjectTasks() {
        List<ProjectTask> projectTasksList = projectTaskRepository.findAll();

        return projectTaskEntitiesToDtos(projectTasksList);
    }

    public ProjectTaskDto findProjectTaskById(UUID id) {
        ProjectTask projectTask = projectTaskRepository.getById(id);

        if (projectTask == null) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }

        return projectTaskEntityToDto(projectTask);
    }

    public ProjectTaskDto deleteProjectTaskById(UUID id) {
        ProjectTask projectTask = projectTaskRepository.getById(id);

        if (projectTask == null) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }

        projectTaskRepository.delete(projectTask);
        ProjectTaskDto deletedProjectTaskDto = projectTaskEntityToDto(projectTask);
        log.info(Constants.deleteProjectTaskMessage(deletedProjectTaskDto));
        return deletedProjectTaskDto;
    }

    public List<ProjectTaskDto> findProjectTasksByProjectId(UUID projectId) {
        return projectTaskEntitiesToDtos(projectTaskRepository.findAllByProjectId(projectId));
    }

    private Project projectDtoToEntity(ProjectDto projectDto) {
        return new Project(
                projectDto.getId(),
                projectDto.getName(),
                projectDto.getDescription(),
                projectDto.getStart_date(),
                projectDto.getEnd_date(),
                projectDto.getDeployed_link(),
                projectDto.getDocumentation_link(),
                projectDto.getCode_link(),
                null);
    }

    private ProjectTask projectTaskDtoToEntity(ProjectTaskDto projectTaskDto) {
        return new ProjectTask(
                projectTaskDto.getProject_id(),
                projectTaskDto.getName(),
                projectTaskDto.getDescription(),
                projectTaskDto.getAcceptance_criteria(),
                projectTaskDto.getPoints(),
                projectTaskDto.getStatus(),
                null
        );
    }

    private ProjectDto projectEntityToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getDeployed_link(),
                project.getDocumentation_link(),
                project.getCodeLink()
        );
    }

    private ProjectTaskDto projectTaskEntityToDto(ProjectTask projectTask) {
        UUID projectId = null;
        if (projectTask.getProject() != null) {
            projectId = projectTask.getProject().getId();
        }
        return new ProjectTaskDto(
                projectTask.getId(),
                projectId,
                projectTask.getName(),
                projectTask.getDescription(),
                projectTask.getAcceptance_criteria(),
                projectTask.getPoints(),
                projectTask.getStatus()
        );
    }

    private List<ProjectTaskDto> projectTaskEntitiesToDtos(List<ProjectTask> projectTasks) {
        List<ProjectTaskDto> projectTaskDtos = new ArrayList<ProjectTaskDto>(Collections.emptyList());
        for (ProjectTask projectTask : projectTasks) {
            ProjectTaskDto projectTaskDto = projectTaskEntityToDto(projectTask);
            projectTaskDtos.add(projectTaskDto);
        }
        return projectTaskDtos;
    }

    private List<ProjectDto> projectEntitiesToDtos(Iterable<Project> projects) {
        List<ProjectDto> projectDtos = new ArrayList<>(Collections.emptyList());
        for (Project project : projects) {
            ProjectDto projectDto = projectEntityToDto(project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }
}
