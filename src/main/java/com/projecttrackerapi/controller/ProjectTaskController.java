package com.projecttrackerapi.controller;

import com.projecttrackerapi.models.GenericResponseModel;
import com.projecttrackerapi.models.ProjectTaskDto;
import com.projecttrackerapi.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseModel> addProjectTaskToBoard(@RequestBody ProjectTaskDto projectTaskDto){
        ProjectTaskDto newProjectTaskDto = projectTaskService.saveOrUpdateProjectTask(projectTaskDto);
        GenericResponseModel responseModel = new GenericResponseModel(201, newProjectTaskDto);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getAllPTs(){
        List<ProjectTaskDto> projectTaskDtos = projectTaskService.getAllProjectTasks();
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDtos);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectTaskById(@PathVariable("projectTaskId") String projectTaskId){
        ProjectTaskDto projectTaskDto = projectTaskService.getProjectTaskById(UUID.fromString(projectTaskId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectTasksByProjectId(@PathVariable("projectId") String projectId){
        List<ProjectTaskDto> projectTaskDto = projectTaskService.getProjectTasksByProjectId(UUID.fromString(projectId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @DeleteMapping("/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> deleteProjectTask(@PathVariable("projectTaskId") String projectTaskId){
        ProjectTaskDto projectTaskDto = projectTaskService.deleteProjectTaskById(UUID.fromString(projectTaskId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }
}
