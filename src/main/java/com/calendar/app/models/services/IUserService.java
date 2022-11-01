package com.calendar.app.models.services;

import com.calendar.app.models.entity.User;
import com.calendar.app.payload.request.SignupRequest;

public interface IUserService {
    User save( SignupRequest signupRequest );
    User findByUsername( String username );
}