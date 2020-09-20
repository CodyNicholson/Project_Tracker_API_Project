package com.projecttrackerapi.controller;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProjectToBoard(@RequestBody Project project){

        Project newProject = projectService.saveOrUpdateProject(project);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Project> getAllPTs(){
        return projectService.findAll();
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProjectById(@PathVariable("projectId") String projectId){
        Project project = projectService.findById(UUID.fromString(projectId));
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") String projectId){
        projectService.delete(UUID.fromString(projectId));
        return new ResponseEntity<String>(Constants.PROJECT_DELETED, HttpStatus.OK);
    }
}
