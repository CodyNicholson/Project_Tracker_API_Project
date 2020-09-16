package com.projecttrackerapi.service;

import com.projecttrackerapi.domain.ProjectEntity;
import com.projecttrackerapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectEntity saveOrUpdateProject(ProjectEntity projectEntity){
        return projectRepository.save(projectEntity);
    }

    public Iterable<ProjectEntity> findAll(){
        return projectRepository.findAll();
    }

    public ProjectEntity findById(Long id){
        return projectRepository.getById(id);
    }

    public void delete(Long id){
        ProjectEntity projectEntity = findById(id);
        projectRepository.delete(projectEntity);
    }
}
