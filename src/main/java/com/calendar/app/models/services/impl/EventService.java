package com.calendar.app.models.services.impl;

import com.calendar.app.exception.RecordNotFountException;
import com.calendar.app.models.entity.Event;
import com.calendar.app.models.entity.User;
import com.calendar.app.models.repository.IEventRepository;
import com.calendar.app.models.services.IEventService;
import com.calendar.app.models.services.IUserService;
import com.calendar.app.security.jwt.AuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @AllArgsConstructor
public class EventService implements IEventService {
    private final IEventRepository eventRepository;
    private final IUserService userService;

    @Override
    @Transactional
    public Event save( Event event ) {
        User foundUser = this.userService.findByUsername( this.getUserName() );
        event.setUser( foundUser );
        return this.eventRepository.save( event );
    }

    @Override
    @Transactional( readOnly = true )
    public List<Event> findAll() {
        UserDetails userLogin = AuthTokenFilter.getUserLoggedIn();
        User foundUser = this.userService.findByUsername( userLogin.getUsername() );
        return this.eventRepository.findAllByUser_Id( foundUser.getId() );
    }

    @Override
    @Transactional
    public Event update( Long id, Event event ) {
        Event foundEvent = this.getEvent( id );
        Event updateEvent = this.buildEvent( foundEvent, event );
        return this.eventRepository.save( updateEvent );
    }

    @Override
    @Transactional
    public Event delete( Long id ) {
        Event foundEvent = this.getEvent( id );
        this.eventRepository.deleteById( id );
        return foundEvent;
    }

    public String getUserName() {
        UserDetails userLogin = AuthTokenFilter.getUserLoggedIn();
        return userLogin.getUsername();
    }

    private Event getEvent( Long id ) {
        Optional<Event> foundEvent = this.eventRepository.findById(id);
        User foundUser = this.userService.findByUsername( this.getUserName() );
        if( foundEvent.isEmpty() || !foundEvent.get().getUser().getUsername().equals( foundUser.getUsername() ) ) {
            throw new RecordNotFountException( "Event", "ID", id.toString() );
        }

        return foundEvent.get();
    }

    public Event buildEvent( Event oldEvent, Event requestEvent ) {
        oldEvent.setTitle( requestEvent.getTitle() != null ? requestEvent.getTitle() : oldEvent.getTitle() );
        oldEvent.setNotes( requestEvent.getNotes()!= null? requestEvent.getNotes() : oldEvent.getNotes() );
        oldEvent.setStart( requestEvent.getStart()!= null? requestEvent.getStart() : oldEvent.getStart() );
        oldEvent.setEnd( requestEvent.getEnd()!= null? requestEvent.getEnd() : oldEvent.getEnd() );

        return oldEvent;
    }
}