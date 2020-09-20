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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_project_id"))
    private Project project;
}


