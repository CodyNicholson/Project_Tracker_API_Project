package com.projecttrackerapi.constants;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;

import java.util.Date;
import java.util.List;

public final class Constants {
    public static final String TODO_STATUS = "TO_DO";

    public String saveOrUpdateProjectMessage(Project project) {
        return "\nTime: " + new Date().toString() + ", \nSaved/Updated Project: " + project.toString();
    }

    public String deleteProjectMessage(Project deletedProject, List<ProjectTask> deletedProjectTasks) {
        return "\nTime: " + new Date().toString() + ", \nDeleted Project: " + deletedProject.toString() + ", and Project Tasks: " + deletedProjectTasks.toString();
    }

    public String saveOrUpdateProjectTaskMessage(ProjectTask projectTask) {
        return "\nTime: " + new Date().toString() + ", \nSaved/Updated Project Task: " + projectTask.toString();
    }

    public String deleteProjectTaskMessage(ProjectTask projectTask) {
        return "\nTime: " + new Date().toString() + ", \nDeleted Project Task: " + projectTask.toString();
    }
}
