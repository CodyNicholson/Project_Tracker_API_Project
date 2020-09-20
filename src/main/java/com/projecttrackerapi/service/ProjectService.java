package com.projecttrackerapi.service;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public Project saveOrUpdateProject(Project project){
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
        return new DeleteProjectResponseModel(deletedProject, deletedProjectTasks);
    }
}
