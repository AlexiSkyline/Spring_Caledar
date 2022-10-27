package com.calendar.app.models.services;

import com.calendar.app.models.entity.User;
import com.calendar.app.payload.request.SignupRequest;

import java.util.Optional;

public interface IUserService {
    User save( SignupRequest signupRequest );
    Optional<User> findByUsername( String username );
    Boolean existsByUsername( String username );
    Boolean existsByEmail( String email );
}