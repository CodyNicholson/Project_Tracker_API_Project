package com.projecttrackerapi.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.error.restCustomExceptions.NotFoundException;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.models.ProjectDto;
import com.projecttrackerapi.models.ProjectTaskDto;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
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
    private ObjectMapper objectMapper;

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
    public DeleteProjectResponseModel deleteProject(UUID id) {
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

    public ProjectTaskDto saveOrUpdateProjectTask(ProjectTaskDto projectTaskDto) {
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

    public ProjectTaskDto findProjectTaskById(UUID id) {
        return projectTaskEntityToDto(projectTaskRepository.getById(id));
    }

    public ProjectTaskDto deleteProjectTaskById(UUID id) {
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
        try {
            return objectMapper.readValue(projectDto.toString(), Project.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException(Constants.INVALID_JSON, e);
        }
    }

    private ProjectTask projectTaskDtoToEntity(ProjectTaskDto projectTaskDto) {
        try {
            return objectMapper.readValue(projectTaskDto.toString(), ProjectTask.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException(Constants.INVALID_JSON, e);
        }
    }

    private ProjectDto projectEntityToDto(Project project) {
        try {
            return objectMapper.readValue(project.toString(), ProjectDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException(Constants.INVALID_JSON, e);
        }
    }

    private ProjectTaskDto projectTaskEntityToDto(ProjectTask projectTask) {
        try {
            return objectMapper.readValue(projectTask.toString(), ProjectTaskDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException(Constants.INVALID_JSON, e);
        }
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
    private void fillTestData() {
        Project projectTrackingProject = new Project(UUID.randomUUID(), "Project Tracking App", "An application I created to track all of my side projects", new Date(), null, "deployedLink1", "docLink1", "codeLink1");
        Project issTrackingProject = new Project(UUID.randomUUID(), "ISS Tracking App", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project battleApp = new Project(UUID.randomUUID(), "Battle App", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project projectDesignerProject = new Project(UUID.randomUUID(), "Project Designer App", "A remodel of drawio", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project tronApp = new Project(UUID.randomUUID(), "Tron App", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project bloggingApp = new Project(UUID.randomUUID(), "Blogging App", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project selfDrivingCarProjectWebsite = new Project(UUID.randomUUID(), "Self Driving Car Project Website", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project clnGithubIoProject = new Project(UUID.randomUUID(), "CodyNicholson.github.io", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project portfolioWebsite = new Project(UUID.randomUUID(), "Portfolio Website", "My portfolio website on Heroku!", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project springBootKafkaProject = new Project(UUID.randomUUID(), "Spring Boot Kafka Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project fastStyleTransferProject = new Project(UUID.randomUUID(), "Fast Style Transfer Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project rixusProject = new Project(UUID.randomUUID(), "Rixus Project", "Voice operated personal assistant - maybe built using Flutter?", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project mindVaultProject = new Project(UUID.randomUUID(), "Mind Vault Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project stockScrapperProject = new Project(UUID.randomUUID(), "Stock Scrapper Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project tetrisProject = new Project(UUID.randomUUID(), "Tetris Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project enodoProject = new Project(UUID.randomUUID(), "Enodo Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project multiplayerTetrisProject = new Project(UUID.randomUUID(), "Multiplayer Tetris Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project memeCreatorProject = new Project(UUID.randomUUID(), "Meme Creator Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project starshotRicochetProject = new Project(UUID.randomUUID(), "Starshot Ricochet Project", "The web based implementation of Benny Breakout's adventure to save the world", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project findingLaneLinesProject = new Project(UUID.randomUUID(), "Finding Lane Lines Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project trafficSignClassifierProject = new Project(UUID.randomUUID(), "Traffic Sign Classifier Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project behavioralCloningProject = new Project(UUID.randomUUID(), "Behavioral Cloning Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project advancedLaneLineFindingProject = new Project(UUID.randomUUID(), "Advanced Lane Line Finding Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project vehicleDetectionProject = new Project(UUID.randomUUID(), "Vehicle Detection Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project extendedKalmanFilterProject = new Project(UUID.randomUUID(), "Extended Kalman Filter Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project unscentedKalmanFilter = new Project(UUID.randomUUID(), "Unscented Kalman Filter Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project kidnappedVehicleProject = new Project(UUID.randomUUID(), "Kidnapped Vehicle Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project pidControllerProject = new Project(UUID.randomUUID(), "PID Controller Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project modelPredictiveControllerProject = new Project(UUID.randomUUID(), "Model Predictive Controller Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project ecommerceProject = new Project(UUID.randomUUID(), "VueJS e-Commerce Platform w/ Stripe integration", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project seleniumAutomatedTestingFramework = new Project(UUID.randomUUID(), "Selenium Automated Testing Framework", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");
        Project collectiveKnowledgeProject = new Project(UUID.randomUUID(), "Collective Knowledge Project", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");

        projectRepository.save(projectTrackingProject);
        projectRepository.save(issTrackingProject);

        ProjectTask task1 = new ProjectTask(projectTrackingProject.getId(), "Add Flyway DB Migrations", "description 1", "AC 1", 3.0, "Status 1", null);
        ProjectTask task2 = new ProjectTask(projectTrackingProject.getId(), "Create New Project Selection Component", "description 2", "AC 2", 1, "Status 2", null);
        ProjectTask task3 = new ProjectTask(projectTrackingProject.getId(), "Add JWT Token Authentication", "description 2", "AC 2", 1, "Status 2", null);
        ProjectTask task4 = new ProjectTask(projectTrackingProject.getId(), "Add Export DB As JSON file", "description 2", "AC 2", 1, "Status 2", null);
        ProjectTask task5 = new ProjectTask(issTrackingProject.getId(), "Add ISS Speed Graph", "description 4", "AC 4", 1, "Status 4", null);
        ProjectTask task6 = new ProjectTask(issTrackingProject.getId(), "Add ISS Most Common Weather Condition Graph", "description 5", "AC 5", 2, "Status 5", "blocked");
        ProjectTask task7 = new ProjectTask(issTrackingProject.getId(), "Add ISS Add Average Temperature", "description 3", "AC 3", 8, "Status 3", "blocked");
        ProjectTask task8 = new ProjectTask(issTrackingProject.getId(), "Add ISS Most Visited Countries", "description 3", "AC 3", 8, "Status 3", "blocked");
        ProjectTask task9 = new ProjectTask(issTrackingProject.getId(), "Add Export DB As JSON File", "description 3", "AC 3", 8, "Status 3", "blocked");

        projectTaskRepository.save(task1);
        projectTaskRepository.save(task2);
        projectTaskRepository.save(task3);
        projectTaskRepository.save(task4);
        projectTaskRepository.save(task5);
        projectTaskRepository.save(task6);
        projectTaskRepository.save(task7);
        projectTaskRepository.save(task8);
        projectTaskRepository.save(task9);
    }
}
