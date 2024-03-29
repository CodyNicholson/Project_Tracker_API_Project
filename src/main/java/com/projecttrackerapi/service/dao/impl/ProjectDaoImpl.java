package com.projecttrackerapi.service.dao.impl;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.dtos.ProjectRequestDto;
import com.projecttrackerapi.dtos.ProjectResponseDto;
import com.projecttrackerapi.dtos.ProjectTaskRequestDto;
import com.projecttrackerapi.dtos.ProjectTaskResponseDto;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import com.projecttrackerapi.service.dao.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        log.info(Constants.createProjectLog(projectRequestDto));
        Project projectToSave = projectDtoToEntity(projectRequestDto);
        Project savedProject = projectRepository.save(projectToSave);
        return projectEntityToDto(savedProject);
    }

    @Override
    public ProjectResponseDto findProjectById(UUID projectId){
        Project projectEntity = projectRepository.getById(projectId);

        if (projectEntity == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        ProjectResponseDto projectDto = projectEntityToDto(projectEntity);

        Constants.getProjectLog(projectDto);

        return projectDto;
    }

    @Override
    public List<ProjectResponseDto> findAllProjects() {
        return projectEntitiesToDtos(projectRepository.findAll());
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, UUID projectId) {
        log.info(Constants.updateProjectLog(projectRequestDto, projectId));
        Project projectToUpdateEntity = projectRepository.getById(projectId);

        if (projectToUpdateEntity == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        Project updatedProjectEntity = projectDtoToEntity(projectRequestDto);
        updatedProjectEntity.setId(projectToUpdateEntity.getId());

        Project savedProject = projectRepository.save(updatedProjectEntity);
        return projectEntityToDto(savedProject);
    }

    @Override
    @Transactional
    public ProjectResponseDto deleteProject(UUID projectId) {
        Project project = projectRepository.getById(projectId);

        if (project == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        List<ProjectTask> projectTasks = projectTaskRepository.getByProjectId(projectId);
        projectTasks.forEach(projectTask -> projectTaskRepository.delete(projectTask));

        projectRepository.delete(project);

        ProjectResponseDto deletedProjectResponseDto = projectEntityToDto(project);
        List<ProjectTaskResponseDto> deletedProjectTaskResponsDtos = projectTaskEntitiesToDtos(projectTasks, projectId);

        log.info(Constants.deleteProjectLog(deletedProjectResponseDto, deletedProjectTaskResponsDtos));

        return deletedProjectResponseDto;
    }

    @Override
    public ProjectTaskResponseDto createProjectTask(ProjectTaskRequestDto projectTaskRequestDto, UUID projectId) {
        log.info(Constants.saveProjectTaskMessage(projectTaskRequestDto));

        Project projectEntity = projectRepository.getById(projectId);

        if (projectEntity == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        ProjectTask projectTask = projectTaskDtoToEntity(projectTaskRequestDto, null, projectId);
        projectTask.setProject_id(projectEntity.getId());

        ProjectTask savedProjectTask = projectTaskRepository.save(projectTask);
        return projectTaskEntityToDto(savedProjectTask, projectId);
    }

    @Override
    public ProjectTaskResponseDto findProjectTaskById(UUID id, UUID projectId) {
        ProjectTask projectTask = projectTaskRepository.getById(id);

        if (projectTask == null) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }

        return projectTaskEntityToDto(projectTask, projectId);
    }

    @Override
    public List<ProjectTaskResponseDto> findProjectTasksByProjectId(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        List<ProjectTaskResponseDto> projectTaskResponseDtos = projectTaskEntitiesToDtos(projectTaskRepository.findAllByProjectId(projectId), projectId);

        if (projectTaskResponseDtos.isEmpty()) {
            throw new NotFoundException(Constants.PROJECT_HAS_NO_TASKS, null);
        }

        return projectTaskResponseDtos;
    }

    @Override
    public ProjectTaskResponseDto updateProjectTask(ProjectTaskRequestDto projectTaskRequestDto, UUID projectTaskId, UUID projectId) {
        log.info(Constants.updateProjectTaskLog(projectTaskRequestDto, projectTaskId));
        ProjectTask projectTaskToUpdate = projectTaskRepository.getById(projectTaskId);

        if (projectTaskToUpdate == null) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }

        ProjectTask updatedProjectTaskEntity = projectTaskDtoToEntity(projectTaskRequestDto, projectTaskId, projectId);
        updatedProjectTaskEntity.setId(projectTaskId);

        ProjectTask savedProjectTask = projectTaskRepository.save(updatedProjectTaskEntity);
        return projectTaskEntityToDto(savedProjectTask, projectId);
    }

    @Override
    public ProjectTaskResponseDto deleteProjectTaskById(UUID projectTaskId, UUID projectId) {
        ProjectTask projectTask = projectTaskRepository.getById(projectTaskId);

        if (projectTask == null) {
            throw new NotFoundException(Constants.PROJECT_TASK_NOT_FOUND, null);
        }

        projectTaskRepository.delete(projectTask);
        ProjectTaskResponseDto deletedProjectTaskResponseDto = projectTaskEntityToDto(projectTask, projectId);
        log.info(Constants.deleteProjectTaskMessage(deletedProjectTaskResponseDto));
        return deletedProjectTaskResponseDto;
    }

    private Project projectDtoToEntity(ProjectRequestDto projectRequestDto) {
        return new Project(
                null,
                projectRequestDto.getName(),
                projectRequestDto.getDescription(),
                projectRequestDto.getStart_date(),
                projectRequestDto.getEnd_date(),
                projectRequestDto.getDeployed_link(),
                projectRequestDto.getDocumentation_link(),
                projectRequestDto.getCode_link(),
                null);
    }

    private ProjectTask projectTaskDtoToEntity(ProjectTaskRequestDto projectTaskRequestDto, UUID projectTaskId, UUID projectId) {
        return new ProjectTask(
                projectTaskId,
                projectId,
                projectTaskRequestDto.getName(),
                projectTaskRequestDto.getDescription(),
                projectTaskRequestDto.getAcceptance_criteria(),
                projectTaskRequestDto.getPoints(),
                projectTaskRequestDto.getStatus()
        );
    }

    private ProjectResponseDto projectEntityToDto(Project project) {
        return new ProjectResponseDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getDeployed_link(),
                project.getDocumentation_link(),
                project.getCode_link(),
                projectTaskEntitiesToDtos(project.getProjectTasks(), project.getId())
        );
    }

    private ProjectTaskResponseDto projectTaskEntityToDto(ProjectTask projectTask, UUID projectId) {
        return new ProjectTaskResponseDto(
                projectTask.getId(),
                projectId,
                projectTask.getName(),
                projectTask.getDescription(),
                projectTask.getAcceptance_criteria(),
                projectTask.getPoints(),
                projectTask.getStatus()
        );
    }

    private List<ProjectTaskResponseDto> projectTaskEntitiesToDtos(List<ProjectTask> projectTasks, UUID projectId) {
        if (projectTasks == null) {
            return Collections.emptyList();
        }

        List<ProjectTaskResponseDto> projectTaskResponsDtos = new ArrayList<ProjectTaskResponseDto>(Collections.emptyList());
        for (ProjectTask projectTask : projectTasks) {
            ProjectTaskResponseDto projectTaskResponseDto = projectTaskEntityToDto(projectTask, projectId);
            projectTaskResponsDtos.add(projectTaskResponseDto);
        }
        return projectTaskResponsDtos;
    }

    private List<ProjectResponseDto> projectEntitiesToDtos(Iterable<Project> projects) {
        List<ProjectResponseDto> projectResponsDtos = new ArrayList<>(Collections.emptyList());
        for (Project project : projects) {
            ProjectResponseDto projectResponseDto = projectEntityToDto(project);
            projectResponsDtos.add(projectResponseDto);
        }
        return projectResponsDtos;
    }
}
