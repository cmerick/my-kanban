package com.ecsolutions.cadastros.persistence.repository;

import com.ecsolutions.cadastros.persistence.entities.Client;
import com.ecsolutions.cadastros.persistence.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, UUID>, JpaSpecificationExecutor<TaskStatus> {
}
