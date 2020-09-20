package com.projecttrackerapi.models;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProjectResponseModel {
    private Project project;
    private List<ProjectTask> projectTaskList;
}