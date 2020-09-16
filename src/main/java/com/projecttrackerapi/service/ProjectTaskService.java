package com.projecttrackerapi.service;
import com.projecttrackerapi.domain.ProjectTaskEntity;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import com.projecttrackerapi.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTaskEntity saveOrUpdateProjectTask(ProjectTaskEntity projectTaskEntity){

        if(projectTaskEntity.getStatus() == null || projectTaskEntity.getStatus().isEmpty()){
            projectTaskEntity.setStatus(Constants.TODO_STATUS);
        }

        return projectTaskRepository.save(projectTaskEntity);
    }

    public Iterable<ProjectTaskEntity> findAll(){
        return projectTaskRepository.findAll();
    }

    public ProjectTaskEntity findById(Long id){
        return projectTaskRepository.getById(id);
    }

    public void delete(Long id){
        ProjectTaskEntity projectTaskEntity = findById(id);
        projectTaskRepository.delete(projectTaskEntity);
    }
}
