package com.projecttrackerapi.dao;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.repository.ProjectRepository;
import com.projecttrackerapi.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ProjectDao {
    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;

    public ProjectDao(ProjectRepository projectRepository, ProjectTaskRepository projectTaskRepository) {
        this.projectRepository = projectRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    @PostConstruct
    @Transactional
    public void fillData(){
        Project project1 = new Project("project1", "description1", new Date(), null, "deployedLink1", "docLink1", "codeLink1");
        Project project2 = new Project("project2", "description2", new Date(), null, "deployedLink2", "docLink2", "codeLink2");

        projectRepository.save(project1);
        projectRepository.save(project2);

        // create tasks
        ProjectTask task1 = new ProjectTask("Summary 1", "AC 1", "Status 1", project1);
        ProjectTask task2 = new ProjectTask("Summary 2", "AC 2", "Status 2", project1);
        ProjectTask task3 = new ProjectTask("Summary 3", "AC 3", "Status 3", project1);
        ProjectTask task4 = new ProjectTask("Summary 4", "AC 4", "Status 4", project2);
        ProjectTask task5 = new ProjectTask("Summary 5", "AC 5", "Status 5", project2);

        projectTaskRepository.save(task1);
        projectTaskRepository.save(task2);
        projectTaskRepository.save(task3);
        projectTaskRepository.save(task4);
        projectTaskRepository.save(task5);
    }
}
