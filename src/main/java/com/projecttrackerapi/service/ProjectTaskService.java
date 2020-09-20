package com.projecttrackerapi.service;

import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import com.projecttrackerapi.constants.Constants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ProjectTaskService {

    private final Constants constants = new Constants();
    private final Logger logger;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTaskService(Logger logger) {
        this.logger = logger;
    }

    public ProjectTask saveOrUpdateProjectTask(ProjectTask projectTask){
        if(projectTask.getStatus() == null || projectTask.getStatus().isEmpty()){
            projectTask.setStatus(Constants.TODO_STATUS);
        }

        logger.info(constants.saveOrUpdateProjectTaskMessage(projectTask));

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findAll(){
        return projectTaskRepository.findAll();
    }

    public ProjectTask findById(UUID id){
        return projectTaskRepository.getById(id);
    }

    public ProjectTask delete(UUID id){
        ProjectTask projectTask = findById(id);
        projectTaskRepository.delete(projectTask);
        logger.info(constants.deleteProjectTaskMessage(projectTask));
        return projectTask;
    }
}
