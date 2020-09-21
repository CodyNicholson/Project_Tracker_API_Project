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
