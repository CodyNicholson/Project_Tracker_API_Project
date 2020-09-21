package com.projecttrackerapi.controller;

import com.projecttrackerapi.models.DeleteProjectResponseModel;
import com.projecttrackerapi.models.GenericResponseModel;
import com.projecttrackerapi.models.ProjectDto;
import com.projecttrackerapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponseModel> addProjectToBoard(@RequestBody ProjectDto project) {
        ProjectDto newProject = projectService.saveOrUpdateProject(project);
        GenericResponseModel responseModel = new GenericResponseModel(201, newProject);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getAllPTs(){
        Iterable<ProjectDto> projects = projectService.getAllProjects();
        GenericResponseModel responseModel = new GenericResponseModel(200, projects);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> getProjectById(@PathVariable("projectId") String projectId){
        ProjectDto project = projectService.getProjectById(UUID.fromString(projectId));
        GenericResponseModel responseModel = new GenericResponseModel(200, project);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseModel> deleteProject(@PathVariable("projectId") String projectId){
        DeleteProjectResponseModel deleteProjectResponseModel = projectService.deleteProjectById(UUID.fromString(projectId));
        GenericResponseModel responseModel = new GenericResponseModel(200, deleteProjectResponseModel);
        return new ResponseEntity<GenericResponseModel>(responseModel, HttpStatus.OK);
    }
}
