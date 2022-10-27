package com.calendar.app.controller;

import com.calendar.app.models.services.IUserService;
import com.calendar.app.payload.request.LoginRequest;
import com.calendar.app.payload.request.SignupRequest;
import com.calendar.app.payload.response.JwtResponse;
import com.calendar.app.payload.response.ResponseHandler;
import com.calendar.app.security.UserDetailsImpl;
import com.calendar.app.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping( "/api/auth" )
@AllArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping( "/login" )
    public ResponseEntity<?> authenticateUser( @Valid @RequestBody LoginRequest loginRequest ) {
        return ResponseHandler.responseBuild( OK, "Welcome", this.authenticate( loginRequest.getUsername(), loginRequest.getPassword() ) );
    }

    @PostMapping( "/register" )
    public ResponseEntity<?> registerUser( @Valid @RequestBody SignupRequest signUpRequest ) {
        if ( this.userService.existsByUsername( signUpRequest.getUsername() ) ) {
            return ResponseEntity
                    .badRequest()
                    .body( "Error: Username is already taken!" );
        }

        if ( this.userService.existsByEmail( signUpRequest.getEmail() ) ) {
            return ResponseEntity
                    .badRequest()
                    .body( "Error: Email is already in use!" );
        }
        this.userService.save( signUpRequest );

        return ResponseHandler.responseBuild( OK, "Account successfully created", this.authenticate( signUpRequest.getUsername(), signUpRequest.getPassword() ) );
    }

    private JwtResponse authenticate( String username, String password ) {
        Authentication authentication = authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken( username, password ) );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority ).toList();

        return new JwtResponse( userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, jwt );
    }
}