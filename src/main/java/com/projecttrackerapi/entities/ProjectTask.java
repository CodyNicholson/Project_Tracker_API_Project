package com.projecttrackerapi.entities;

import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTask extends EntityWithUUID {
    private UUID projectId;
    private String name;
    private String description;
    private String acceptanceCriteria;
    private double points;
    private String status;
    private String blockers;
}


