package com.calendar.app.controller;

import com.calendar.app.models.entity.Event;
import com.calendar.app.models.services.IEventService;
import com.calendar.app.payload.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createEvent( @Valid @RequestBody Event event ) {
        return ResponseHandler.responseBuild( CREATED, "Event Created Successfully", this.eventService.save( event ) );
    }

    @GetMapping
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> getEvents() {
        return ResponseHandler.responseBuild( OK, "Requested All Event are given here", this.eventService.findAll() );
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