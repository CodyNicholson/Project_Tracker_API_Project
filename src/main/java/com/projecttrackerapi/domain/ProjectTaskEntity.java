package com.projecttrackerapi.domain;

import javax.persistence.*;

@Entity
@Table(name = "project_tasks")
public class ProjectTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "acceptance_criteria")
    private String acceptanceCriteria;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name ="project_id", nullable = false)
    private ProjectEntity projectEntity;

    public ProjectTaskEntity() {
    }

    public ProjectTaskEntity(Long id, String summary, String acceptanceCriteria, String status) {
        this.id = id;
        this.summary = summary;
        this.acceptanceCriteria = acceptanceCriteria;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

