package com.projecttrackerapi.constants;

import com.projecttrackerapi.entities.Project;
import com.projecttrackerapi.entities.ProjectTask;
import com.projecttrackerapi.models.ProjectDto;
import com.projecttrackerapi.models.ProjectTaskDto;

import java.util.Date;
import java.util.List;

public final class Constants {
    // Project Statuses
    public static final String TODO_STATUS = "TO_DO";

    // Rest Error Messages
    public static final String REST_BAD_REQUEST = "400: Bad Request";
    public static final String REST_FORBIDDEN = "403: Forbidden";
    public static final String REST_NOT_FOUND = "404: Not Found";
    public static final String REST_CONFLICT = "409: Conflict";
    public static final String REST_MEDIA_TYPE_NOT_SUPPORTED = "415: Media Type Not Supported";
    public static final String REST_INTERNAL_SERVER_ERROR = "500: Internal Server Error";
    public static final String REST_SERVICE_UNAVAILABLE = "503: Service Unavailable";

    // Detailed Error Messages
    public static final String PROJECT_MUST_HAVE_NAME = "The project must have a name.";
    public static final String PROJECT_NOT_FOUND = "The project was not found.";
    public static final String PROJECT_FOR_TASK_NOT_FOUND = "The project for this task was not found.";

    // Log Messages
    public static String saveOrUpdateProjectMessage(ProjectDto project) {
        return "\nTime: " + new Date().toString() + ", \nSaved/Updated Project: " + project.toString();
    }

    public static String deleteProjectMessage(ProjectDto deletedProject, List<ProjectTaskDto> deletedProjectTasks) {
        return "\nTime: " + new Date().toString() + ", \nDeleted Project: " + deletedProject.toString() + ", and Project Tasks: " + deletedProjectTasks.toString();
    }

    public static String saveOrUpdateProjectTaskMessage(ProjectTaskDto projectTask) {
        return "\nTime: " + new Date().toString() + ", \nSaved/Updated Project Task: " + projectTask.toString();
    }

    public static String deleteProjectTaskMessage(ProjectTaskDto projectTask) {
        return "\nTime: " + new Date().toString() + ", \nDeleted Project Task: " + projectTask.toString();
    }
}
