package com.codynicholson.projectboard.repository;

import com.codynicholson.projectboard.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cody on 1/23/2019.
 */

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    ProjectTask getById(Long id);
}
