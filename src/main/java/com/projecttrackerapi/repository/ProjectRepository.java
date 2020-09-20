package com.projecttrackerapi.repository;

import com.projecttrackerapi.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<Project, UUID> {
    Project getById(UUID id);
}
