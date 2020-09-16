package com.projecttrackerapi.controller;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.domain.ProjectEntity;
import com.projecttrackerapi.domain.ProjectTaskEntity;
import com.projecttrackerapi.service.ProjectService;
import com.projecttrackerapi.service.ProjectTaskService;
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
    public ResponseEntity<?> addProjectToBoard(@Valid @RequestBody ProjectEntity projectEntity, BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        ProjectEntity newProject = projectService.saveOrUpdateProject(projectEntity);

        return new ResponseEntity<ProjectEntity>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<ProjectEntity> getAllPTs(){
        return projectService.findAll();
    }

    @GetMapping("/{pt_id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long pt_id){
        ProjectEntity projectEntity = projectService.findById(pt_id);
        return new ResponseEntity<ProjectEntity>(projectEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{pt_id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId){
        projectService.delete(projectId);
        return new ResponseEntity<String>(Constants.PROJECT_DELETED, HttpStatus.OK);
    }
}
