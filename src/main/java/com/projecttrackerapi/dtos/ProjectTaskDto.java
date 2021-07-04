package com.projecttrackerapi.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class ProjectTaskDto {
    private UUID id;
    private UUID projectId;
    private String name;
    private String description;
    private String acceptanceCriteria;
    private double points;
    private String status;
    private String blockers;
}
