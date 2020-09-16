package com.projecttrackerapi.controller;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.service.ProjectTaskService;
import com.projecttrackerapi.domain.ProjectTaskEntity;
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
@RequestMapping("/task")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/")
    public ResponseEntity<?> addProjectTaskToBoard(@Valid @RequestBody ProjectTaskEntity projectTaskEntity, BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        ProjectTaskEntity newPT = projectTaskService.saveOrUpdateProjectTask(projectTaskEntity);

        return new ResponseEntity<ProjectTaskEntity>(newPT, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<ProjectTaskEntity> getAllPTs(){
        return projectTaskService.findAll();
    }

    @GetMapping("/{project_task_id}")
    public ResponseEntity<?> getProjectTaskById(@PathVariable Long project_task_id){
        ProjectTaskEntity projectTaskEntity = projectTaskService.findById(project_task_id);
        return new ResponseEntity<ProjectTaskEntity>(projectTaskEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{project_task_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable Long project_task_id){
        projectTaskService.delete(project_task_id);
        return new ResponseEntity<String>(Constants.PROJECT_TASK_DELETED, HttpStatus.OK);
    }
}
