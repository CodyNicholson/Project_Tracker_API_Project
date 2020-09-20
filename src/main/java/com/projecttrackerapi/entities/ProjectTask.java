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
    private String summary;
    private String acceptanceCriteria;
    private String status;
}


