package com.projecttrackerapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProjectResponseModel {
    private ProjectDto project;
    private List<ProjectTaskDto> projectTaskList;
}