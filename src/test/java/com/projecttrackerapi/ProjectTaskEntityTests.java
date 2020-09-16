package com.projecttrackerapi;

import com.projecttrackerapi.domain.ProjectTaskEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTaskEntityTests {

    private ProjectTaskEntity projectTaskEntity;

    @Before
    public void createTasks() {
        projectTaskEntity = new ProjectTaskEntity((long) 1, "Summary 1", "AC 1", "Status 1");
    }

	@Test
	public void getIdTest() {
        assertTrue(projectTaskEntity.getId() == 1);
	}
}

