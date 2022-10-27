package com.calendar.app.models.repository;

import com.calendar.app.models.entity.Role;
import com.calendar.app.models.entity.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(TypeRole name );
}