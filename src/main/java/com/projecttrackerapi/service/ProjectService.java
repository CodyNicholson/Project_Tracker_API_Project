package com.projecttrackerapi.service;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final Logger logger;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectService(Logger logger) {
        this.logger = logger;
    }

    public Project saveOrUpdateProject(Project project){
        logger.info(new Constants().saveOrUpdateProjectMessage(project));
        return projectRepository.save(project);
    }

    public Iterable<Project> findAll(){
        return projectRepository.findAll();
    }

    public Project findById(UUID id){
        return projectRepository.getById(id);
    }

    @Transactional
    public DeleteProjectResponseModel delete(UUID id){
        Project deletedProject = projectRepository.getById(id);
        List<ProjectTask> deletedProjectTasks = projectTaskRepository.deleteByProjectId(id);
        projectRepository.delete(deletedProject);
        logger.info(new Constants().deleteProjectMessage(deletedProject, deletedProjectTasks));
        return new DeleteProjectResponseModel(deletedProject, deletedProjectTasks);
    }
}
