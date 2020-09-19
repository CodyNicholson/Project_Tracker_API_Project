package com.projecttrackerapi.service;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        return projectRepository.save(project);
    }

    public Iterable<Project> findAll(){
        return projectRepository.findAll();
    }

    public Project findById(Long id){
        return projectRepository.getById(id);
    }

    public void delete(Long id){
        Project project = findById(id);
        projectRepository.delete(project);
    }
}
