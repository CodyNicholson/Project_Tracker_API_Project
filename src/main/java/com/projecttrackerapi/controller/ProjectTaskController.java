package com.projecttrackerapi.controller;

import com.projecttrackerapi.dtos.GenericResponseModel;
import com.projecttrackerapi.dtos.ProjectTaskDto;
import com.projecttrackerapi.service.projecttask.impl.ProjectTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskServiceImpl projectTaskService;

    @PostMapping("/task/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseModel> addProjectTaskToBoard(@RequestBody ProjectTaskDto projectTaskDto){
        ProjectTaskDto newProjectTaskDto = projectTaskService.saveOrUpdateProjectTask(projectTaskDto);
        GenericResponseModel responseModel = new GenericResponseModel(201, newProjectTaskDto);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/task/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getAllPTs(){
        List<ProjectTaskDto> projectTaskDtos = projectTaskService.getAllProjectTasks();
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDtos);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/task/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectTaskById(@PathVariable("projectTaskId") String projectTaskId){
        ProjectTaskDto projectTaskDto = projectTaskService.getProjectTaskById(UUID.fromString(projectTaskId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/task/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectTasksByProjectId(@PathVariable("projectId") String projectId){
        List<ProjectTaskDto> projectTaskDto = projectTaskService.getProjectTasksByProjectId(UUID.fromString(projectId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @DeleteMapping("/task/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> deleteProjectTask(@PathVariable("projectTaskId") String projectTaskId){
        ProjectTaskDto projectTaskDto = projectTaskService.deleteProjectTaskById(UUID.fromString(projectTaskId));
        GenericResponseModel responseModel = new GenericResponseModel(200, projectTaskDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
