package com.calendar.app.models.services.impl;

import com.calendar.app.models.entity.Event;
import com.calendar.app.models.entity.User;
import com.calendar.app.models.repository.IEventRepository;
import com.calendar.app.models.repository.IUserRepository;
import com.calendar.app.models.services.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @AllArgsConstructor
public class EventService implements IEventService {
    private final IEventRepository eventRepository;
    private final IUserRepository userRepository;

    @Override
    public Event save( Event event, String userName ) {
        Optional<User> foundUser = this.userRepository.findByUsername( userName );
        event.setUser( foundUser.get() );

        return this.eventRepository.save( event );
    }

    @Override
    public List<Event> findAll( String userName ) {
        Optional<User> foundUser = this.userRepository.findByUsername( userName );
        return this.eventRepository.findAllByUser_Id( foundUser.get().getId() );
    }

    @Override
    public Event update( Long id, Event event ) {
        Optional<Event> foundEvent = this.eventRepository.findById( id );

        Event updateEvent = this.buildEvent( foundEvent.get(), event );
        return this.eventRepository.save( updateEvent );
    }

    @Override
    public Event delete( Long id ) {
        Optional<Event> foundEvent = this.eventRepository.findById( id );

        this.eventRepository.deleteById( id );
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
