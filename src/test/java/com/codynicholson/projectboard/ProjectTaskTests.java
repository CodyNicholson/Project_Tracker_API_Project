package com.codynicholson.projectboard;

import com.codynicholson.projectboard.domain.ProjectTask;
import com.codynicholson.projectboard.repository.ProjectTaskRepository;
import com.codynicholson.projectboard.service.ProjectTaskService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTaskTests {

    private ProjectTask projectTask;

    @Before
    public void createTasks() {
        projectTask = new ProjectTask((long) 1, (long) 3, "Summary 1", "AC 1", "Status 1");
    }

	@Test
	public void getIdTest() {
        assertTrue(projectTask.getId() == 1);
	}
}

