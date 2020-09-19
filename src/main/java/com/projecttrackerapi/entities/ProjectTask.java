package com.projecttrackerapi.entities;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTask extends EntityWithUUID {
    private String summary;
    private String acceptanceCriteria;
    private String status;

    @ManyToOne
    @JoinColumn(name ="project_id", nullable = false)
    private Project project;
}


