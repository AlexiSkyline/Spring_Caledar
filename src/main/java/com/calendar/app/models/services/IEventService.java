package com.calendar.app.models.services;

import com.calendar.app.models.entity.Event;

import java.util.List;

public interface IEventService {
    Event save( Event event );
    List<Event> findAll();
    Event update( Long id, Event event );
    Event delete( Long id );
}