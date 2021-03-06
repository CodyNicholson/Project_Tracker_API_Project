package com.projecttrackerapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends EntityWithUUID {
    private UUID id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String deployedLink;
    private String documentationLink;
    private String codeLink;
}
