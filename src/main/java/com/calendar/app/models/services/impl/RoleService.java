package com.calendar.app.models.services.impl;

import com.calendar.app.models.entity.Role;
import com.calendar.app.models.entity.TypeRole;
import com.calendar.app.models.repository.IRoleRepository;
import com.calendar.app.models.services.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @AllArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    @Transactional( readOnly = true )
    public Optional<Role> findByName( TypeRole name ) {
        return this.roleRepository.findByName( name );
    }
}