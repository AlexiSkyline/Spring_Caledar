package com.calendar.app.models.services;

import com.calendar.app.models.entity.Role;
import com.calendar.app.models.entity.TypeRole;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName( TypeRole name );
}