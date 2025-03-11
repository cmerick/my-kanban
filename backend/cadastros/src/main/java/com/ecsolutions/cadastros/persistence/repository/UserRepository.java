package com.ecsolutions.cadastros.persistence.repository;

import com.ecsolutions.cadastros.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Query(value = "SELECT entity FROM User entity WHERE UPPER(entity.email) = UPPER(:email) ")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT entity FROM User entity WHERE entity.keycloakId = :keycloakId ")
    Optional<User> findByKeycloakId(@Param("keycloakId") UUID keycloakId);
}
