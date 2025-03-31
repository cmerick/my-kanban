package com.ecsolutions.cadastros.persistence.repository;

import com.ecsolutions.cadastros.persistence.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

    Optional<Collection<Project>> findByClientId(UUID clientId);
}
