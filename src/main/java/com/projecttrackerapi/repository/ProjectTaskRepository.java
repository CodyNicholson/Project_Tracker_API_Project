package com.projecttrackerapi.repository;

import com.projecttrackerapi.entities.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
    @Query(value = "SELECT * FROM projecttask pt WHERE pt.id = :id", nativeQuery = true)
    ProjectTask getById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM projecttask pt WHERE pt.project_id = :projectId", nativeQuery = true)
    List<ProjectTask> findAllByProjectId(@Param("projectId") UUID projectId);

    @Query(value = "DELETE FROM projecttask pt WHERE pt.project_id = :projectId", nativeQuery = true)
    List<ProjectTask> deleteByProjectId(@Param("projectId") UUID projectId);
}
