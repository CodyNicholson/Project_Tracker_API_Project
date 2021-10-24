package com.projecttrackerapi.controller;

import com.projecttrackerapi.dtos.GenericResponseDto;
import com.projecttrackerapi.dtos.ProjectTaskRequestDto;
import com.projecttrackerapi.dtos.ProjectTaskResponseDto;
import com.projecttrackerapi.service.projecttask.impl.ProjectTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskServiceImpl projectTaskService;

    @PostMapping("/{projectId}/task/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseDto> addProjectTaskToBoard(
            @RequestBody ProjectTaskRequestDto projectTaskRequestDto,
            @PathVariable("projectId") String projectId
    ){
        ProjectTaskResponseDto newProjectTaskResponseDto = projectTaskService.createProjectTask(projectTaskRequestDto, projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.CREATED.value(), newProjectTaskResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}/task/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> getProjectTaskById(
            @PathVariable("projectTaskId") String projectTaskId,
            @PathVariable("projectId") String projectId
    ){
        ProjectTaskResponseDto projectTaskResponseDto = projectTaskService.getProjectTaskById(projectTaskId, projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projectTaskResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/task/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> getProjectTasksByProjectId(
            @PathVariable("projectId") String projectId
    ){
        List<ProjectTaskResponseDto> projectTaskResponseDto = projectTaskService.getProjectTasksByProjectId(projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projectTaskResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PutMapping("/{projectId}/task/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> updateProjectTask(
            @PathVariable("projectTaskId") String projectTaskId,
            @PathVariable("projectId") String projectId,
            @RequestBody ProjectTaskRequestDto projectTaskRequestDto
    ){
        ProjectTaskResponseDto projectTaskResponseDto = projectTaskService.updateProjectTask(projectTaskRequestDto, projectTaskId, projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projectTaskResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/task/{projectTaskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> deleteProjectTask(
            @PathVariable("projectTaskId") String projectTaskId,
            @PathVariable("projectId") String projectId
    ){
        ProjectTaskResponseDto projectTaskResponseDto = projectTaskService.deleteProjectTaskById(projectTaskId, projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projectTaskResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
