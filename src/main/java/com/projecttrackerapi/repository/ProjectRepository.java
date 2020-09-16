package com.projecttrackerapi.repository;

import com.projecttrackerapi.domain.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    ProjectEntity getById(Long id);
}
