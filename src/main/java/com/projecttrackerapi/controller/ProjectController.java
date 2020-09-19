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

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/")
    public ResponseEntity<?> addProjectToBoard(@Valid @RequestBody Project project, BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        Project newProject = projectService.saveOrUpdateProject(project);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllPTs(){
        return projectService.findAll();
    }

    @GetMapping("/{pt_id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long pt_id){
        Project project = projectService.findById(pt_id);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{pt_id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId){
        projectService.delete(projectId);
        return new ResponseEntity<String>(Constants.PROJECT_DELETED, HttpStatus.OK);
    }
}
