package com.projecttrackerapi.controller;

import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.models.GenericResponseModel;
import com.projecttrackerapi.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseModel> addProjectTaskToBoard(@RequestBody ProjectTask projectTask){
        ProjectTask newProjectTask = projectTaskService.saveOrUpdateProjectTask(projectTask);
        GenericResponseModel responseModel = new GenericResponseModel(201, newProjectTask);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getAllPTs(){
        Iterable<ProjectTask> projectTasks = projectTaskService.findAll();
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTasks);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{project_task_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectTaskById(@PathVariable String project_task_id){
        ProjectTask projectTask = projectTaskService.findById(UUID.fromString(project_task_id));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTask);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @DeleteMapping("/{project_task_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> deleteProjectTask(@PathVariable String project_task_id){
        ProjectTask projectTask = projectTaskService.delete(UUID.fromString(project_task_id));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTask);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }
}
