package com.calendar.app.security;

import com.calendar.app.models.entity.User;
import com.calendar.app.models.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername( username )
                .orElseThrow(() -> new UsernameNotFoundException( "User Not Found with username: " + username ));

        return UserDetailsImpl.build( user );
    }
}