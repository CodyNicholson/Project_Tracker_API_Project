package com.projecttrackerapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDto {
    private UUID id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String deployed_link;
    private String documentation_link;
    private String code_link;
    private List<ProjectTaskResponseDto> project_tasks;
}
