package com.projecttrackerapi.repository;

import com.projecttrackerapi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query(value = "SELECT * FROM project p WHERE p.id = :id", nativeQuery = true)
    Project getById(@Param("id") UUID id);
}
