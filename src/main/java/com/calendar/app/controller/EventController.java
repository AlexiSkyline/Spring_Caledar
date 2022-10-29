package com.calendar.app.controller;

import com.calendar.app.models.entity.Event;
import com.calendar.app.models.services.IEventService;
import com.calendar.app.payload.response.ResponseHandler;
import com.calendar.app.security.jwt.AuthTokenFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping( "/api/event" )
@AllArgsConstructor
public class EventController {
    private final IEventService eventService;

    @PostMapping
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event, HttpServletRequest request ) {
        UserDetails userLogin = AuthTokenFilter.getUserLoggedIn();
        return ResponseHandler.responseBuild( CREATED, "Event Created Successfully", this.eventService.save( event, userLogin.getUsername()  ) );
    }

    @GetMapping
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> getEvents() {
        UserDetails userLogin = AuthTokenFilter.getUserLoggedIn();
        return ResponseHandler.responseBuild( OK, "Requested All Event are given here", this.eventService.findAll( userLogin.getUsername() ) );
    }

    @PutMapping("/{id}")
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> updateEvent( @PathVariable Long id, @Valid @RequestBody Event event ) {
        return ResponseHandler.responseBuild( CREATED, "Event Update SuccessFully", this.eventService.update( id, event ) );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> deleteEvent( @PathVariable Long id ) {
        return ResponseHandler.responseBuild( OK, "Event Delete SuccessFully", this.eventService.delete( id ) );
    }
}