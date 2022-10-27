package com.calendar.app.models.services.impl;

import com.calendar.app.models.entity.Role;
import com.calendar.app.models.entity.User;
import com.calendar.app.models.repository.IRoleRepository;
import com.calendar.app.models.repository.IUserRepository;
import com.calendar.app.models.services.IUserService;
import com.calendar.app.payload.request.SignupRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.calendar.app.models.entity.TypeRole.*;

@Service @AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save( SignupRequest signupRequest ) {
        String password = this.passwordEncoder.encode( signupRequest.getPassword() );
        User user = new User( signupRequest.getUsername(), signupRequest.getEmail(), password, this.addRoleUser( signupRequest.getRoles() ));

        return this.userRepository.save( user );
    }

    @Override
    @Transactional( readOnly = true )
    public Optional<User> findByUsername( String username ) {
        return this.userRepository.findByUsername( username );
    }

    @Override
    @Transactional
    public Boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername( username );
    }

    @Override
    @Transactional
    public Boolean existsByEmail( String email ) {
        return this.userRepository.existsByEmail( email );
    }

    private Set<Role> addRoleUser( Set<String> shortNameRoles ) {
        Set<Role> roles = new HashSet<>();

        if ( shortNameRoles == null ) {
            Role userRole = this.roleRepository.findByName( ROLE_USER )
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            shortNameRoles.forEach( role -> {
                switch( role ) {
                    case "admin" -> {
                        Role adminRole = this.roleRepository.findByName( ROLE_ADMIN )
                                .orElseThrow(() -> new RuntimeException( "Error: Role is not found." ));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = this.roleRepository.findByName( ROLE_MODERATOR )
                                .orElseThrow(() -> new RuntimeException( "Error: Role is not found." ));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = this.roleRepository.findByName( ROLE_USER )
                                .orElseThrow(() -> new RuntimeException( "Error: Role is not found." ));
                        roles.add(userRole);
                    }
                }
            });
        }

        return roles;
    }
}