package com.calendar.app.models.repository;

import com.calendar.app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername( String username );
    Boolean existsByUsername( String username );
    Boolean existsByEmail( String email );
}