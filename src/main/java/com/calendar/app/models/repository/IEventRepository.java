package com.calendar.app.models.repository;

import com.calendar.app.models.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUser_Id( Long id );
}