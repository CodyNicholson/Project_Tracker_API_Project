package com.projecttrackerapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTaskResponseDto {
    private UUID id;
    private UUID project_id;
    private String name;
    private String description;
    private String acceptance_criteria;
    private double points;
    private String status;
}
