package com.projecttrackerapi.repository;

import com.projecttrackerapi.domain.ProjectTaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTaskEntity, Long> {
    ProjectTaskEntity getById(Long id);
}
