package com.projecttrackerapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends EntityWithUUID {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String deployedLink;
    private String documentationLink;
    private String codeLink;
}
