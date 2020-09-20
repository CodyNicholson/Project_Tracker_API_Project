package com.projecttrackerapi.repository;

import com.projecttrackerapi.entities.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    ProjectTask getById(Long id);
    List<ProjectTask> findAllByProjectId(UUID projectId);
    List<ProjectTask> deleteByProjectId(UUID projectId);
}
