package com.projecttrackerapi.controller;

import com.projecttrackerapi.dtos.ProjectRequestDto;
import com.projecttrackerapi.dtos.GenericResponseDto;
import com.projecttrackerapi.dtos.ProjectResponseDto;
import com.projecttrackerapi.service.project.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseDto> addProjectToBoard(@RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto newProject = projectService.createProject(projectRequestDto);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.CREATED.value(), newProject);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> getProjectById(@PathVariable("projectId") String projectId){
        ProjectResponseDto projectResponseDto = projectService.readProjectById(projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projectResponseDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> getAllProjects(){
        Iterable<ProjectResponseDto> projects = projectService.readAllProjects();
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), projects);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> updateProject(
            @PathVariable("projectId") String projectId,
            @RequestBody ProjectRequestDto projectRequestDto
    ) {
        ProjectResponseDto newProject = projectService.updateProject(projectRequestDto, projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.CREATED.value(), newProject);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseDto> deleteProject(@PathVariable("projectId") String projectId){
        ProjectResponseDto deleteProjectResponseModel = projectService.deleteProjectById(projectId);
        GenericResponseDto responseModel = new GenericResponseDto(HttpStatus.OK.value(), deleteProjectResponseModel);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
