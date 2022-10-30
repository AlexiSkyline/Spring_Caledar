package com.calendar.app.models.services.impl;

import com.calendar.app.exception.FieldAlreadyUsedException;
import com.calendar.app.exception.RecordNotFountException;
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

import java.util.Optional;

import static com.calendar.app.models.entity.TypeRole.*;

@Service @AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save( SignupRequest signupRequest ) {
        this.validateDuplicateCredentials( signupRequest );
        String password = this.passwordEncoder.encode( signupRequest.getPassword() );
        User user = new User( signupRequest.getUsername(), signupRequest.getEmail(), password, this.addRoleUser() );

        return this.userRepository.save( user );
    }

    @Override
    @Transactional( readOnly = true )
    public User findByUsername( String username ) {
        Optional<User> foundUser = this.userRepository.findByUsername( username );
        if( foundUser.isEmpty() ) {
            throw new RecordNotFountException( "User", "UserName", username );
        }

        return foundUser.get();
    }

    private void validateDuplicateCredentials( SignupRequest signupRequest ) {
        this.userRepository.findByUsername( signupRequest.getUsername() ).ifPresent(( data ) -> {
            throw new FieldAlreadyUsedException( "Username", data.getUsername(), "User" );
        });
        this.userRepository.findAllByEmail( signupRequest.getEmail() ).ifPresent(( data ) -> {
            throw new FieldAlreadyUsedException( "Email", data.getEmail(), "User" );
        });
    }

    private Role addRoleUser() {
        return this.roleRepository.findByName( ROLE_USER )
                .orElseGet(() -> this.roleRepository.save( new Role( ROLE_USER ) ));
    }
}