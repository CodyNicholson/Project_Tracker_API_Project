package com.projecttrackerapi;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTaskTests {

    private ProjectTask projectTask;

    @Before
    public void createProjectWithTasks() {
        Project project = new Project("name", "description", new Date(), null, "deployedLink", "docLink", "codeLink");
        projectTask = new ProjectTask("Summary 1", "AC 1", "Status 1", project);
    }

	@Test
	public void getIdTest() {
        assertTrue(projectTask.getStatus().equals(""));
	}
}

