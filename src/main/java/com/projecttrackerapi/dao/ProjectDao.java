package com.projecttrackerapi.dao;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.models.ProjectDto;
import com.projecttrackerapi.models.ProjectTaskDto;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class ProjectDao {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger;

    public ProjectDao(Logger logger) {
        this.logger = logger;
    }

    public ProjectDto saveOrUpdateProject(ProjectDto project){
        logger.info(Constants.saveOrUpdateProjectMessage(project));
        Project savedProject = projectRepository.save(projectDtoToEntity(project));
        return projectEntityToDto(savedProject);
    }

    public List<ProjectDto> findAllProjects(){
        return projectEntitiesToDtos(projectRepository.findAll());
    }

    public ProjectDto findProjectById(UUID id){
        return projectEntityToDto(projectRepository.getById(id));
    }

    @Transactional
    public DeleteProjectResponseModel deleteProject(UUID id){
        Project project = projectRepository.getById(id);

        if (project == null) {
            throw new NotFoundException(Constants.PROJECT_NOT_FOUND, null);
        }

        List<ProjectTask> projectTasks = projectTaskRepository.deleteByProjectId(id);
        projectRepository.delete(project);

        ProjectDto deletedProjectDto = projectEntityToDto(project);
        List<ProjectTaskDto> deletedProjectTaskDtos = projectTaskEntitiesToDtos(projectTasks);

        logger.info(Constants.deleteProjectMessage(deletedProjectDto, deletedProjectTaskDtos));
        return new DeleteProjectResponseModel(deletedProjectDto, deletedProjectTaskDtos);
    }

    public ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto){
        logger.info(Constants.saveOrUpdateProjectTaskMessage(projectTaskDto));
        ProjectTask savedProjectTask = projectTaskRepository.save(projectTaskDtoToEntity(projectTaskDto));
        return projectTaskEntityToDto(savedProjectTask);
    }

    public List<ProjectTaskDto> findAllProjectTasks() {
        Iterable<ProjectTask> projectTasksIterable = projectTaskRepository.findAll();
        List<ProjectTask> projectTasksList = new ArrayList<ProjectTask>();
        projectTasksIterable.forEach(projectTasksList::add);
        return projectTaskEntitiesToDtos(projectTasksList);
    }

    public ProjectTaskDto findProjectTaskById(UUID id){
        return projectTaskEntityToDto(projectTaskRepository.getById(id));
    }

    public ProjectTaskDto deleteProjectTaskById(UUID id){
        ProjectTask projectTask = projectTaskRepository.getById(id);
        projectTaskRepository.delete(projectTask);
        ProjectTaskDto deletedProjectTaskDto = projectTaskEntityToDto(projectTask);
        logger.info(Constants.deleteProjectTaskMessage(deletedProjectTaskDto));
        return deletedProjectTaskDto;
    }

    public List<ProjectTaskDto> findProjectTasksByProjectId(UUID projectId) {
        return projectTaskEntitiesToDtos(projectTaskRepository.findAllByProjectId(projectId));
    }

    private Project projectDtoToEntity(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }

    private ProjectTask projectTaskDtoToEntity(ProjectTaskDto projectTaskDto) {
        return modelMapper.map(projectTaskDto, ProjectTask.class);
    }

    private ProjectDto projectEntityToDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    private ProjectTaskDto projectTaskEntityToDto(ProjectTask projectTask) {
        return modelMapper.map(projectTask, ProjectTaskDto.class);
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
        List<ProjectDto> projectDtos = new ArrayList<ProjectDto>(Collections.emptyList());
        for (Project project : projects) {
            ProjectDto projectDto = projectEntityToDto(project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    @PostConstruct
    @Transactional
    private void fillTestData(){
        Project project1 = new Project(UUID.randomUUID(), "project1", "description1", new Date(), null, "deployedLink1", "docLink1", "codeLink1");
        Project project2 = new Project(UUID.randomUUID(), "project2", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");

        projectRepository.save(project1);
        projectRepository.save(project2);

        ProjectTask task1 = new ProjectTask(project1.getId(), "name 1", "description 1", "AC 1", 3.0, "Status 1", null);
        ProjectTask task2 = new ProjectTask(project1.getId(), "name 2", "description 2", "AC 2", 1, "Status 2", null);
        ProjectTask task3 = new ProjectTask(project2.getId(), "name 3", "description 3", "AC 3", 8, "Status 3", "blocked");
        ProjectTask task4 = new ProjectTask(project2.getId(), "name 4", "description 4", "AC 4", 1, "Status 4", null);
        ProjectTask task5 = new ProjectTask(project2.getId(), "name 5", "description 5", "AC 5", 2, "Status 5", "blocked");

        projectTaskRepository.save(task1);
        projectTaskRepository.save(task2);
        projectTaskRepository.save(task3);
        projectTaskRepository.save(task4);
        projectTaskRepository.save(task5);
    }
}
